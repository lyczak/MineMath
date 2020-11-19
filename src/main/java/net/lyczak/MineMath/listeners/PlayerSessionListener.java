package net.lyczak.MineMath.listeners;

import net.lyczak.MineMath.plot.PlayerSessionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerSessionListener implements Listener {
    private PlayerSessionManager psm;

    public PlayerSessionListener(PlayerSessionManager psm) {
        this.psm = psm;
    }
/*
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        psm.remove(event.getPlayer());
    }

 */
}
