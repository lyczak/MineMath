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

public class SurfaceCommand extends CommandBase {
    private PlayerSessionManager sessionManager;

    public SurfaceCommand(MineMath plugin) {
        super(plugin, "surface");
        this.sessionManager = plugin.getPlayerSessionManager();
    }

    public boolean onCommand(CommandSender sender,
                             Command cmd,
                             String label,
                             String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerSession session = sessionManager.get(player);

            if (args.length != 3) {
                player.sendMessage(ChatColor.RED + "This command requires three " +
                        "mathematical expressions as arguments.");
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
            for (int i = 0; i < args.length; i++) {
                r[i] = new Function("r_" + i, args[i], "u", "v", "t");
                for (Function f : session.getFunctions().values()) {
                    r[i].addFunctions(f);
                }
                if (r[i].checkSyntax() == Expression.SYNTAX_ERROR_OR_STATUS_UNKNOWN) {
                    badSyntax = true;
                    player.sendMessage(ChatColor.RED + "Invalid syntax: " + args[i]);
                }
            }
            if(badSyntax) {
                return false;
            }

            PlotOptions o = session.getOptions();

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

    @Override
    public String getHelp() {
        return ChatColor.RED + "/m2 surface 2*u-1 v f(u,v)";
    }
}
