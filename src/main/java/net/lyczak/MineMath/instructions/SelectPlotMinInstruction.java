package net.lyczak.MineMath.instructions;

import net.lyczak.MineMath.ToolFactory;
import net.lyczak.MineMath.plot.Plot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SelectPlotMinInstruction implements PlayerInstruction<PlayerInteractEvent> {
    private Plot plot;

    public SelectPlotMinInstruction(Plot plot) {
        this.plot = plot;
    }

    @Override
    public boolean tryComplete(PlayerInteractEvent event) {
        if (!event.hasItem())
            return false;

        if (!event.getItem().isSimilar(ToolFactory.getWand()))
            return false;

        if (!(event.getAction() == Action.LEFT_CLICK_BLOCK))
            return false;

        plot.setMinimum(event.getClickedBlock().getLocation());
        event.getPlayer().getInventory().remove(ToolFactory.getWand());
        event.setCancelled(true);

        return true;
    }

    @Override
    public void instruct(Player player) {
        player.getInventory().addItem(ToolFactory.getWand());

        player.sendMessage(String.format("%sDefining new plotting area...",
                ChatColor.BLUE));
        player.sendMessage(String.format("%sUse the %s%s%s%s to left click on the " +
                        "minimum (bottom corner) block of the plotting area.",
                ChatColor.BLUE,
                ChatColor.RESET,
                ToolFactory.getWand().getItemMeta().getDisplayName(),
                ChatColor.RESET,
                ChatColor.BLUE));
    }
}
