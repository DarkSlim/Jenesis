package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public class Rtr extends SystemInstruction {
    @Override
    public void handle() {
        cpu.setCCR(cpu.memory.getWord(cpu.getSP()));
        cpu.setSP(cpu.getSP() + 2);

        cpu.setPC(cpu.memory.getLong(cpu.getSP()));
        cpu.setSP(cpu.getSP() + 4);
    }
}
