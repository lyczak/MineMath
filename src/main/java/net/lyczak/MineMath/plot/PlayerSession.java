package net.lyczak.MineMath.plot;

import net.lyczak.MineMath.instructions.PlayerInstruction;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerSession extends PlayerSessionBase {
    private Queue<PlayerInstruction> instructionQueue = new LinkedList<PlayerInstruction>();

    public Queue<PlayerInstruction> getInstructionQueue() {
        return instructionQueue;
    }
}
