package net.lyczak.MineMath.plot;

import net.lyczak.MineMath.instructions.PlayerInstruction;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PlayerSession extends PlayerSessionBase {
    private Queue<PlayerInstruction> instructionQueue = new LinkedList<PlayerInstruction>();
    private List<PlayerInstruction> instructionList = new LinkedList<PlayerInstruction>();

    public Queue<PlayerInstruction> getInstructionQueue() {
        return instructionQueue;
    }

    public List<PlayerInstruction> getInstructionList() {
        return instructionList;
    }
}
