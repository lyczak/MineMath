package net.lyczak.MineMath;

import net.lyczak.MineMath.commands.FunctionCommand;
import net.lyczak.MineMath.commands.PlotCommand;
import net.lyczak.MineMath.commands.RangeCommand;
import net.lyczak.MineMath.commands.SurfaceCommand;
import net.lyczak.MineMath.instructions.InstructionManager;
import net.lyczak.MineMath.listeners.PlayerSessionListener;
import net.lyczak.MineMath.listeners.WandListener;
import net.lyczak.MineMath.plot.PlayerSessionManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MineMath extends JavaPlugin {

    private PlayerSessionManager playerSessionMgr = new PlayerSessionManager();
    private InstructionManager instructionMgr = new InstructionManager(playerSessionMgr);
    private CommandHandler commandHandler = new CommandHandler(this);

    @Override
    public void onEnable() {
        getLogger().info("MineMath is starting up...");

        saveDefaultConfig();

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerSessionListener(playerSessionMgr), this);
        pm.registerEvents(new WandListener(this), this);

        this.getCommand("m2").setExecutor(commandHandler);
        registerCommands();

        getLogger().info("MineMath has started!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MineMath is shutting down...");

        getLogger().info("MineMath has shut down!");
    }

    private void registerCommands() {
        commandHandler.register(new FunctionCommand(this));
        commandHandler.register(new RangeCommand(this));
        commandHandler.register(new SurfaceCommand(this));
        commandHandler.register(new PlotCommand(this));
    }

    public PlayerSessionManager getPlayerSessionManager() {
        return playerSessionMgr;
    }

    public InstructionManager getInstructionManager() {
        return instructionMgr;
    }
}
