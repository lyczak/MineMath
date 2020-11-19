package net.lyczak.MineMath.plot;

import net.lyczak.MineMath.Matrix;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class Plot {
    private static final double DEFAULT_SAMPLING_RATE = 4; // Samples per meter range
    private static final Vector ZERO_VECTOR = new Vector(0, 0, 0);
    private static final Vector NORMALIZED_BOUNDING_VECTOR = new Vector(1, 1, 1);

    private Location minimum;
    private Location maximum;
    private Vector range;
    private Matrix basis;

    public Plot() { }

    public Plot(Location minimum, Location maximum) {
        this.minimum = centerInBlock(minimum.clone());
        this.maximum = centerInBlock(maximum.clone());
        this.range = this.maximum.clone()
                .subtract(this.minimum)
                .toVector();
    }

    public void plot(PlotOptions options) {
        PlotMaterialProvider mProv = options.getMaterialProvider();

        basis = new Matrix(range);

        //double volume = range.getX() * range.getY() * range.getZ();
        int defaultSamples = (int) (range.length() * DEFAULT_SAMPLING_RATE);
        int uSamples = (options.getUSamples() == null) ? defaultSamples : options.getUSamples();
        int vSamples = (options.getVSamples() == null) ? defaultSamples : options.getVSamples();

        this.clear();

        double du = 1.0 / uSamples;
        double dv = 1.0 / vSamples;
        double u = 0;
        for (int i = 0; i <= uSamples; i++) {
            double v = 0;
            for (int j = 0; j <= vSamples; j++) {
                Vector r = options.calcNormalizedPosition(u, v);

                if(r.isInAABB(ZERO_VECTOR, NORMALIZED_BOUNDING_VECTOR)) {
                    Location l = toLocation(r);

                    Block b = l.getBlock();
                    Material m = mProv.getPoint(r);
                    b.setType(m);
                }
                v += dv;
            }

            u += du;
        }



        // Draw axis
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

        double da = Math.min(du, dv);
        int axisSamples = defaultSamples / 4;
        for (int i = 0; i < axes.length; i++) {
            double a = 0;
            for (int j = 0; j <= axisSamples; j++) {
                Vector r = axes[i].clone().multiply(a).add(options.getNormalizedAxesPosition());
                if(r.isInAABB(ZERO_VECTOR, NORMALIZED_BOUNDING_VECTOR)) {
                    Location l = toLocation(r);

                    Block b = l.getBlock();
                    Material m = mProv.getAxis(i);
                    b.setType(m);
                }
                a += da;
            }
        }
        toLocation(options.getNormalizedAxesPosition())
                .getBlock().setType(mProv.getOrigin());
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
//        return minimum.clone().add(
//                normalizedPosition.clone().multiply(range));
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
