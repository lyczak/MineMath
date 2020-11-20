package net.lyczak.MineMath.instructions;

import net.lyczak.MineMath.plot.PlayerSession;
import net.lyczak.MineMath.plot.PlayerSessionManager;
import org.bukkit.event.player.PlayerEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Queue;

public class InstructionManager {
    private PlayerSessionManager psm;

    public InstructionManager(PlayerSessionManager psm) {
        this.psm = psm;
    }

    /**
     * Given a PlayerEvent, find that Player's instructionQueue and
     * instructionList. If event completes the head PlayerInstruction of
     * their instructionQueue, return true. Otherwise, return true at
     * the first instruction in their instructionList that completes.
     * @param event a PlayerEvent presumably belonging to a player with a session and instructions
     * @return true if a PlayerInstruction successfully completes, false otherwise
     */
    public boolean tryComplete(PlayerEvent event) {
        PlayerSession session = psm.get(event.getPlayer());
        if (session == null) {
            return false;
        }

        Queue<PlayerInstruction> queue = session.getInstructionQueue();
        PlayerInstruction queueHead = queue.peek();
        if (queueHead == null) {
            return false;
        }

        if (tryCompleteInstruction(queueHead, event)) {
            queue.remove();

            PlayerInstruction next = queue.peek();
            if (next != null) {
                next.instruct(event.getPlayer());
            }
            return true;
        }

        List<PlayerInstruction> list = session.getInstructionList();
        for (PlayerInstruction instruction : list) {
            if (tryCompleteInstruction(instruction, event)) {
                return true;
            }
        }

        return false;
    }

    private boolean tryCompleteInstruction(PlayerInstruction instruction, PlayerEvent event) {
        Type[] types = instruction.getClass().getGenericInterfaces();
        for (Type ifaceType : types) { // Usually only one item but just in case...
            ParameterizedType ifacePType = (ParameterizedType) ifaceType;

            if(!PlayerInstruction.class.isAssignableFrom((Class<?>) ifacePType.getRawType())) {
                continue; // we're only really looking for PlayerInstruction<E>
            }

            Class<?> eventClass = (Class<?>) ifacePType.getActualTypeArguments()[0]; // What's E?
            if (eventClass.isAssignableFrom(event.getClass())) { // does event's class extend E?
                return instruction.tryComplete(event);
            }
        }
        return false;
    }
}
