package net.lyczak.MineMath.commands;

import net.lyczak.MineMath.MineMath;
import net.lyczak.MineMath.ToolFactory;
import net.lyczak.MineMath.instructions.PlayerInstruction;
import net.lyczak.MineMath.instructions.SelectPlotMaxInstruction;
import net.lyczak.MineMath.instructions.SelectPlotMinInstruction;
import net.lyczak.MineMath.plot.PlayerSession;
import net.lyczak.MineMath.plot.PlayerSessionManager;
import net.lyczak.MineMath.plot.Plot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Queue;

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
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return false;
        }

        Player player = (Player) sender;
        PlayerSession session = sessionManager.get(player);

        Queue<PlayerInstruction> iq = session.getInstructionQueue();

        Plot plot = new Plot(plugin);
        iq.add(new SelectPlotMinInstruction(plot));
        iq.add(new SelectPlotMaxInstruction(plot, session));
        iq.element().instruct(player);

        return true;
    }

    @Override
    public String getHelp() {
        return "/m2 plot";
    }
}
