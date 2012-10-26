package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public class Illegal extends SystemInstruction {

    @Override
    public void handle() {
        cpu.isSupervisor = true;
        cpu.setSP(cpu.getSP() - 4);

        cpu.memory.setLong(cpu.getSP(), cpu.getPC());

        cpu.setSP(cpu.getSP() - 2);

        cpu.memory.setWord(cpu.getSP(), cpu.getSR());

        cpu.setPC(cpu.memory.getWord(0x10));
    }
}
