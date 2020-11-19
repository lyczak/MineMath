package net.lyczak.MineMath.instructions;

import net.lyczak.MineMath.ToolFactory;
import net.lyczak.MineMath.plot.PlayerSession;
import net.lyczak.MineMath.plot.Plot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectPlotMaxInstruction implements PlayerInstruction<PlayerInteractEvent> {
    private Plot plot;
    private PlayerSession session;

    public SelectPlotMaxInstruction(Plot plot, PlayerSession session) {
        this.plot = plot;
        this.session = session;
    }

    @Override
    public boolean tryComplete(PlayerInteractEvent event) {
        if (!event.hasItem())
            return false;

        if (!event.getItem().isSimilar(ToolFactory.getWand()))
            return false;

        if (!(event.getAction() == Action.LEFT_CLICK_BLOCK))
            return false;

        plot.setMaximum(event.getClickedBlock().getLocation());
        session.setActivePlot(plot);
        event.getPlayer().getInventory().remove(ToolFactory.getWand());
        event.getPlayer().sendMessage(ChatColor.GREEN + "Plotting area set!");

        event.setCancelled(true);
        
        return true;
    }

    @Override
    public void instruct(Player player) {
        player.getInventory().addItem(ToolFactory.getWand());

        player.sendMessage(String.format("%sNow, use the %s%s%s%s to left click on the " +
                        "maximum (opposite top corner) block of the plotting area.",
                ChatColor.BLUE,
                ChatColor.RESET,
                ToolFactory.getWand().getItemMeta().getDisplayName(),
                ChatColor.RESET,
                ChatColor.BLUE));
    }
}
