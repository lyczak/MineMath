package net.lyczak.MineMath.commands;

import net.lyczak.MineMath.MineMath;
import net.lyczak.MineMath.plot.PlayerSessionManager;
import net.lyczak.MineMath.plot.Plot;
import net.lyczak.MineMath.plot.PlotOptions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class OptionCommand extends CommandBase implements TabCompleter {
    private static final String TIME_VARYING = "animate";
    private static final String REPEATING = "repeat";
    private static final String DURATION = "duration";

    private PlayerSessionManager sessionManager;

    public OptionCommand(MineMath plugin) {
        super(plugin, "option");
        this.sessionManager = plugin.getPlayerSessionManager();
    }

    public boolean onCommand(CommandSender sender,
                             Command cmd,
                             String label,
                             String[] args) {
        if(sender instanceof Player) {
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Invalid number of arguments.");
                return false;
            }

            PlotOptions o = sessionManager.get((Player) sender).getOptions();

            String opt = args[0];
            String val = args[1];

            if (opt.equalsIgnoreCase("animate")) {
                Boolean b = tryParseBooleanOption(val, sender);
                if (b != null) {
                    o.setTimeVarying(b);
                }
                sender.sendMessage(ChatColor.GREEN + "Plots will " +
                        (o.isTimeVarying() ? "" : "not ") + "vary with respect to time (t).");

            } else if (opt.equalsIgnoreCase("repeating")) {
                Boolean b = tryParseBooleanOption(val, sender);
                if (b != null) {
                    o.setTimeRepeating(b);
                }
                sender.sendMessage(ChatColor.GREEN + "Animated plots will " +
                        (o.isTimeRepeating() ? "" : "not ") + "repeat.");

            } else if (opt.equalsIgnoreCase("duration")) {
                try {
                    float duration = Float.parseFloat(val);
                    if (duration > 1 && duration <= 30) {
                        int totalSamples = Math.round(duration * Plot.SAMPLES_PER_SECOND);
                        o.setTSamples(totalSamples);
                        sender.sendMessage(String.format("%s Plot animations will last %.1f seconds " +
                                        "and will proceed at %.1f frames per second.",
                                ChatColor.GREEN,
                                duration,
                                Plot.SAMPLES_PER_SECOND));
                        return true;
                    }
                } catch (NumberFormatException ignored) { }
                sender.sendMessage(ChatColor.RED + "Invalid option value: " + opt);
                sender.sendMessage(ChatColor.RED +
                        "The animation duration (in seconds) must be a decimal number between 1 and 30.");
            } else {
                sender.sendMessage(ChatColor.RED + "Invalid option: " + opt);
                //sender.sendMessage("Valid options include i (or x), j (or y), k (or z), u, or v.");
                return false;
            }
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "This command must be run by a player.");
        }
        return false;
    }

    private Boolean tryParseBooleanOption(String str, CommandSender sender) {
        if (str.equalsIgnoreCase("true")) {
            return true;
        } else if (str.equalsIgnoreCase("false")) {
            return false;
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid option value: " + str);
            sender.sendMessage(ChatColor.RED + "The option value must be either true or false.");
        }
        return null;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender,
                                      Command command,
                                      String alias,
                                      String[] args) {
        if(args.length <= 1) {
            return Arrays.asList(TIME_VARYING, REPEATING, DURATION);
        } else if (args.length == 2) {
            String opt = args[0];
            if (opt.equalsIgnoreCase(TIME_VARYING)
            || opt.equalsIgnoreCase(REPEATING)) {
                return Arrays.asList("true", "false");
            } else if (opt.equalsIgnoreCase(DURATION)) {
                return Arrays.asList("5");
            }
        }
        return null;
    }

    @Override
    public String getHelp() {
        return "/m2 option ";
    }
}
