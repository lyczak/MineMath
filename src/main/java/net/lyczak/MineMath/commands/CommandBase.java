package net.lyczak.MineMath.commands;

import net.lyczak.MineMath.MineMath;
import org.bukkit.command.CommandExecutor;

public abstract class CommandBase implements CommandExecutor {
    protected MineMath plugin;
    private String name;

    public CommandBase(MineMath plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public abstract String getHelp();
}
