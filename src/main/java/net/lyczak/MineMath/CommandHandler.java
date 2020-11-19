package net.lyczak.MineMath;

import net.lyczak.MineMath.commands.CommandBase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;

public class CommandHandler implements CommandExecutor, TabCompleter {
    private MineMath plugin;
    private Map<String, CommandBase> playerCommands = new HashMap<String, CommandBase>();
    private List<String> commandNames = new ArrayList<String>();

    public CommandHandler(MineMath plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender,
                             Command cmd,
                             String label,
                             String[] args) {
        if (args.length > 0) {

            CommandBase c = playerCommands.get(args[0]);
            if(c != null) {

                boolean success = c.onCommand(sender, cmd, label,
                        Arrays.copyOfRange(args, 1, args.length));

                if(!success) {
                    sender.sendMessage(ChatColor.RED + "Usage: " + c.getHelp());
                }
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "Please enter a valid sub-command.");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender,
                                      Command command,
                                      String alias,
                                      String[] args) {
        if (args.length <= 1) {
            return commandNames;
        } else {
            CommandBase c = playerCommands.get(args[0]);
            if(c instanceof TabCompleter) {
                return ((TabCompleter) c).onTabComplete(commandSender, command, alias,
                        Arrays.copyOfRange(args, 1, args.length));
            }
        }
        return null;
    }

    public void register(CommandBase c) {
        playerCommands.put(c.getName(), c);
        commandNames.add(c.getName());
    }


}
