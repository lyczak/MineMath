package net.lyczak.MineMath.commands;

import net.lyczak.MineMath.MineMath;
import net.lyczak.MineMath.plot.Plot;
import net.lyczak.MineMath.plot.PlotOptions;
import net.lyczak.MineMath.plot.PlayerSession;
import net.lyczak.MineMath.plot.PlayerSessionManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class PlotCommand extends CommandBase {
    private PlayerSessionManager sessionManager;

    public PlotCommand(MineMath plugin) {
        super(plugin, "plot");
        this.sessionManager = plugin.getPlayerSessionManager();
    }

    public boolean onCommand(CommandSender sender,
                             Command cmd,
                             String label,
                             String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerSession session = sessionManager.get(player);

            boolean singleFunction = args.length == 1;
            if (!(args.length == 3 || singleFunction)) {
                player.sendMessage(ChatColor.RED + "This command requires three " +
                        "mathematical expressions in terms of u, v, and t as arguments.");
                player.sendMessage(ChatColor.RED + "Alternatively, you can provide a " +
                        "single expression in terms of x, y, and t.");
                return false;
            }

            Plot plot = session.getActivePlot();
            if (plot == null) {
                player.sendMessage(ChatColor.RED + "No plotting area is defined!\nUse " +
                        ChatColor.BLUE + "/m2 plot" + ChatColor.RED + " to define one");
                return true;
            }

            boolean badSyntax = false;
            Function[] r = new Function[3];

            if (singleFunction) {
                r[2] = new Function("r_2", args[0], "x", "y", "t");
                badSyntax = hasBadSyntax(r[2], player);
            } else {
                for (int i = 0; i < 3; i++) {
                    r[i] = new Function("r_" + i, args[i], "u", "v", "t");
                    for (Function f : session.getFunctions().values()) {
                        r[i].addFunctions(f);
                    }
                    badSyntax = hasBadSyntax(r[i], player);
                }
            }

            if(badSyntax) {
                return false;
            }

            PlotOptions o = session.getOptions();

            if (singleFunction) {
                r[0] = new Function("r_0", "x", "x", "y", "t");
                r[1] = new Function("r_1", "y", "x", "y", "t");
                o.setUVForSurfaces();
            }

            o.setIFunction(r[0]);
            o.setJFunction(r[1]);
            o.setKFunction(r[2]);
            o.setAxesCenter();

            plot.clear();
            plot.plot(o);

            return true;
        } else {
            sender.sendMessage("This command must be run by a player.");
            return false;
        }
    }

    private boolean hasBadSyntax(Function f, Player p) {
        if (f.checkSyntax() == Expression.SYNTAX_ERROR_OR_STATUS_UNKNOWN) {
            p.sendMessage(ChatColor.RED + "Invalid syntax: " + f.getFunctionExpressionString());
            return true;
        }
        return false;
    }

    @Override
    public String getHelp() {
        return ChatColor.RED + "/m2 plot 2*u-1 v f(u,v)\n" +
                "/m2 plot cos(t) sin(t) u-t\n" +
                "/m2 plot e(-x^2-y^2)\n" +
                "/m2 plot x*y*sin(t)";
    }
}
