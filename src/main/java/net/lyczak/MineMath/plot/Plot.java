package net.lyczak.MineMath.plot;

import net.lyczak.MineMath.Matrix;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Plot {
    private static final int TICKS_PER_SAMPLE = 4;
    public static final float SAMPLES_PER_SECOND = 20.0f / TICKS_PER_SAMPLE; // Assuming 20 ticks per second
    private static final double SAMPLES_PER_DISTANCE = 4; // Samples per block distance of range
    private static final Vector ZERO_VECTOR = new Vector(0, 0, 0);
    private static final Vector NORMALIZED_BOUNDING_VECTOR = new Vector(1, 1, 1);

    private Plugin plugin;
    private Location minimum;
    private Location maximum;
    private Vector range;
    private Matrix basis;

    public Plot(Plugin plugin) {
        this.plugin = plugin;
    }

    public Plot(Plugin plugin, Location minimum, Location maximum) {
        this(plugin);

        this.minimum = centerInBlock(minimum.clone());
        this.maximum = centerInBlock(maximum.clone());
        this.range = this.maximum.clone()
                .subtract(this.minimum)
                .toVector();
    }

    public void plot(final PlotOptions options) {
        basis = Matrix.fromDiagonal(range);

        if (options.isTimeVarying()) {
            final int tSamples = options.getTSamples();
            final double dt = options.getTRange() / tSamples;

            BukkitRunnable task = new BukkitRunnable() {
                private int i = 0;
                private double t = options.getTMin();

                @Override
                public void run() {
                    if (i > tSamples) {
                        if(options.isTimeRepeating()) {
                            i = 0;
                            t = options.getTMin();
                        } else {
                            this.cancel();
                            return;
                        }
                    }

                    drawFrame(options, t);
                    t += dt;
                    i++;
                }
            };

            task.runTaskTimer(plugin, 0, TICKS_PER_SAMPLE);
        } else {
            drawFrame(options, 0);
        }
    }

    private void drawFrame(final PlotOptions options, double t) {
        PlotMaterialProvider mProv = options.getMaterialProvider();

        int defaultSamples = (int) (range.length() * SAMPLES_PER_DISTANCE);
        int uSamples = (options.getUSamples() == null) ? defaultSamples : options.getUSamples();
        int vSamples = (options.getVSamples() == null) ? defaultSamples : options.getVSamples();

        this.clear();

        double du = 1.0 / uSamples;
        double dv = 1.0 / vSamples;
        double u = 0;
        for (int i = 0; i <= uSamples; i++) {
            double v = 0;
            for (int j = 0; j <= vSamples; j++) {
                final Vector r = options.calcNormalizedPosition(u, v, t);

                if (r.isInAABB(ZERO_VECTOR, NORMALIZED_BOUNDING_VECTOR)) {
                    final Material m = mProv.getPoint(r);

                    Bukkit.getScheduler().runTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Location l = Plot.this.toLocation(r);

                            Block b = l.getBlock();
                            b.setType(m);
                        }
                    });
                }
                v += dv;
            }

            u += du;
        }

        drawAxis(options, defaultSamples);
    }

    public void clear() {
        Vector trueMin = Vector.getMinimum(minimum.toVector(), maximum.toVector());
        Vector trueMax = Vector.getMaximum(minimum.toVector(), maximum.toVector());

        for (int x = trueMin.getBlockX(); x <= trueMax.getBlockX(); x++) {
            for (int y = trueMin.getBlockY(); y <= trueMax.getBlockY(); y++) {
                for (int z = trueMin.getBlockZ(); z <= trueMax.getBlockZ(); z++) {
                    Location l = new Location(minimum.getWorld(), x, y, z);
                    l.getBlock().setType(Material.AIR);
                }
            }
        }
    }

    private Location toLocation(Vector normalizedPosition) {
        return minimum.clone().add(basis.multiply(normalizedPosition));
    }

    private void drawAxis(PlotOptions options, int samples)
    {
        PlotMaterialProvider mProv = options.getMaterialProvider();

        Vector[] axes;
        if (options.areAxesSwapped()) {
            axes = new Vector[] {
                    new Vector(1, 0, 0),
                    new Vector(0, 0, 1),
                    new Vector(0, 1, 0)
            };
        } else {
            axes = new Vector[] {
                    new Vector(1, 0, 0),
                    new Vector(0, 1, 0),
                    new Vector(0, 0, 1)
            };
        }


        double da = 0.20 / Math.sqrt(samples); // TODO: Make scale modifiable
        for (int i = 0; i < axes.length; i++) {
            double a = 0;
            for (int j = 0; j <= samples; j++) {
                final Vector r = axes[i].clone().multiply(a).add(options.getNormalizedAxesPosition());
                if(r.isInAABB(ZERO_VECTOR, NORMALIZED_BOUNDING_VECTOR)) {
                    final Material m = mProv.getAxis(i);

                    Bukkit.getScheduler().runTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            Location l = Plot.this.toLocation(r);

                            Block b = l.getBlock();
                            b.setType(m);
                        }
                    });
                }
                a += da;
            }
        }
        toLocation(options.getNormalizedAxesPosition())
                .getBlock().setType(mProv.getOrigin());
    }

    private static Location centerInBlock(Location l) {
        l.setX(l.getBlockX() + 0.5);
        l.setY(l.getBlockY() + 0.5);
        l.setY(l.getBlockY() + 0.5);
        return l;
    }

    public Location getMinimum() {
        return minimum;
    }

    public void setMinimum(Location minimum) {
        this.minimum = centerInBlock(minimum);
    }

    public Location getMaximum() {
        return maximum;
    }

    public void setMaximum(Location maximum) {
        this.maximum = centerInBlock(maximum);
        this.range = this.maximum.clone()
                .subtract(this.minimum)
                .toVector();
    }
}
