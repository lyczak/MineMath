package net.lyczak.MineMath.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.mariuszgromada.math.mxparser.Function;

public class CurveCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender,
                             Command cmd,
                             String label,
                             String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length != 3) {
                player.sendMessage("This command requires three functions of `t` as arguments.");
                return false;
            }

            Function fi = new Function("i", args[0], "t");
            Function fj = new Function("j", args[1], "t");
            Function fk = new Function("k", args[2], "t");

            Location o = player.getLocation(); // Origin
            o.setX(o.getBlockX() + 0.5);
            o.setY(o.getBlockY() + 0.5);
            o.setZ(o.getBlockZ() + 0.5);

            Vector scale = new Vector(10, 10, 10);

            clearPlot(o, scale);

            double t = 0;
            int samples = 100;
            double dt = 1.0 / samples;

            Vector uBound = new Vector(1, 1, 1);
            Vector lBound = new Vector(-1, -1, -1);
            for (int i = 0; i <= samples; i++) {
                Vector r = new Vector(
                        fi.calculate(t),
                        fk.calculate(t),
                        fj.calculate(t)); // Flip Y & Z

                if(r.isInAABB(lBound, uBound)) {
                    r.multiply(scale);

                    Location l = o.clone();
                    l.add(r);

                    Block b = l.getBlock();
                    b.setType(Material.WHITE_WOOL);
                }

                t += dt;
            }

            drawAxes(o, 3);

            return true;
        } else {
            sender.sendMessage("This command must be run by a player.");
            return false;
        }
    }

    private void drawAxes(Location o, int length) {
        Vector[] bases = {
                new Vector(1, 0, 0),
                new Vector(0, 0, 1),
                new Vector(0, 1, 0),
        };
        Material[] materials = {
                Material.PINK_WOOL,
                Material.LIME_WOOL,
                Material.LIGHT_BLUE_WOOL
        };

        o.getBlock().setType(Material.BLACK_WOOL);

        for (int i = 0; i < 3; i++) {
            for (int n = 1; n <= length; n++) {
                Vector v = bases[i].clone().multiply(n);
                Location l = o.clone().add(v);
                l.getBlock().setType(materials[i]);
            }
        }
    }

    private void clearPlot(Location o, Vector s) {
        for (int i = -s.getBlockX(); i <= s.getBlockX(); i++) {
            for (int j = -s.getBlockZ(); j <= s.getBlockZ(); j++) {
                for (int k = -s.getBlockY(); k <= s.getBlockY(); k++) {
                    Location l = o.clone().add(new Vector(i, k, j));
                    l.getBlock().setType(Material.AIR);
                }
            }
        }
    }
}
