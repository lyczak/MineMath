package net.lyczak.MineMath.instructions;

import net.lyczak.MineMath.plot.PlayerSession;
import net.lyczak.MineMath.plot.PlayerSessionManager;
import org.bukkit.event.player.PlayerEvent;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Queue;

public class InstructionManager {
    private PlayerSessionManager psm;

    public InstructionManager(PlayerSessionManager psm) {
        this.psm = psm;
    }

    public boolean complete(PlayerEvent event) {
        PlayerSession session = psm.get(event.getPlayer());
        if (session == null) {
            return false;
        }

        Queue<PlayerInstruction> queue = session.getInstructionQueue();
        PlayerInstruction instruction = queue.peek();
        if (instruction == null) {
            return false;
        }

        Type[] types = instruction.getClass().getGenericInterfaces();
        for (Type ifaceType : types) {
            ParameterizedType ifacePType = (ParameterizedType) ifaceType;

            if(!PlayerInstruction.class.isAssignableFrom((Class<?>) ifacePType.getRawType())) {
                continue;
            }

            Class<?> eventClass = (Class<?>) ifacePType.getActualTypeArguments()[0];
            if (eventClass.isAssignableFrom(event.getClass())) {
                if(instruction.tryComplete(event)) {
                    queue.remove();

                    PlayerInstruction next = queue.peek();
                    if (next != null) {
                        next.instruct(event.getPlayer());
                    }

                    return true;
                }
            }
        }

        return false;
    }
}
