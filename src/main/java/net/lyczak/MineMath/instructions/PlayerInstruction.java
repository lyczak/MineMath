package net.lyczak.MineMath.instructions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public interface PlayerInstruction<T extends PlayerEvent> {
    boolean tryComplete(T event);
    void instruct(Player player);
}
