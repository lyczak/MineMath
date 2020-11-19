package net.lyczak.MineMath.listeners;

import net.lyczak.MineMath.MineMath;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class WandListener implements Listener {
    private MineMath plugin;

    public WandListener(MineMath plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.useItemInHand() == Event.Result.DENY)
            return;

        if (event.getHand() == EquipmentSlot.OFF_HAND)
            return;

        plugin.getInstructionManager().complete(event);
    }
}
