package net.lyczak.MineMath.commands;

import net.lyczak.MineMath.MineMath;
import net.lyczak.MineMath.plot.PlotOptions;
import net.lyczak.MineMath.plot.PlayerSessionManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class RangeCommand extends CommandBase implements TabCompleter {
    private PlayerSessionManager sessionManager;

    public RangeCommand(MineMath plugin) {
        super(plugin, "range");
        this.sessionManager = plugin.getPlayerSessionManager();
    }

    public boolean onCommand(CommandSender sender,
                             Command cmd,
                             String label,
                             String[] args) {
        if(sender instanceof Player) {
            if (args.length == 3) {
                PlotOptions o = sessionManager.get((Player) sender).getOptions();

                double min, max;
                try {
                    min = Double.parseDouble(args[1]);
                    max = Double.parseDouble(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "The last two arguments are not valid numbers.");
                    return false;
                }

                char rangeParam = args[0].toLowerCase().charAt(0);
                switch (rangeParam) {
                    case 'i':
                    case 'x':
                        o.setIBounds(min, max);
                        break;
                    case 'j':
                    case 'y':
                        o.setJBounds(min, max);
                        break;
                    case 'k':
                    case 'z':
                        o.setKBounds(min, max);
                        break;
                    case 'u':
                        o.setUBounds(min, max);
                        break;
                    case 'v':
                        o.setVBounds(min, max);
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "Invalid range parameter: " + rangeParam);
                        sender.sendMessage("Valid options include i (or x), j (or y), k (or z), u, or v.");
                        return false;
                }

                sender.sendMessage(ChatColor.GREEN + args[1] + " < " + rangeParam + " < " + args[2]);

                return true;
            }
            sender.sendMessage(ChatColor.RED + "Invalid number of arguments.");
        } else {
            sender.sendMessage(ChatColor.RED + "This command must be run by a player.");
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender,
                                      Command command,
                                      String alias,
                                      String[] args) {
        if(args.length <= 1) {
            return Arrays.asList("u", "v", "i", "j", "k", "x", "y", "z");
        } else if (args.length == 2)  {
            return Arrays.asList("0", "-1");
        } else if (args.length == 3) {
            return Arrays.asList("1");
        }
        return null;
    }

    @Override
    public String getHelp() {
        return "/m2 range [i|x|j|y|k|z|u|v] [minimum] [maximum]";
    }
}
