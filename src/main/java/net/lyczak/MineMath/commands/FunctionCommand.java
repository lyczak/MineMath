package net.lyczak.MineMath.commands;

import net.lyczak.MineMath.MineMath;
import net.lyczak.MineMath.plot.PlayerSessionManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mariuszgromada.math.mxparser.Function;

public class FunctionCommand extends CommandBase {
    private PlayerSessionManager sessionManager;

    public FunctionCommand(MineMath plugin) {
        super(plugin, "function");
        this.sessionManager = plugin.getPlayerSessionManager();
    }

    public boolean onCommand(CommandSender sender,
                             Command cmd,
                             String label,
                             String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "This command requires " +
                        "one function as an argument (be sure to remove spaces).");
                return false;
            }

            Function f = new Function(args[0]);

            if(f.checkSyntax() == Function.SYNTAX_ERROR_OR_STATUS_UNKNOWN) {
                player.sendMessage(ChatColor.RED + "Invalid syntax!");
                return false;
            }

            sessionManager.get(player).getFunctions().put(f.getFunctionName(), f);

            player.sendMessage(ChatColor.GREEN  + args[0]);

            return true;
        } else {
            sender.sendMessage("This command must be run by a player.");
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "/m2 def f(x,y)=y^2*cos(x)";
    }
}