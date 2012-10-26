package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public class Rts extends SystemInstruction {
    @Override
    public void handle() {
        cpu.setPC(cpu.memory.getLong(cpu.getSP()));
        cpu.setSP(cpu.getSP() + 4);
    }
}
