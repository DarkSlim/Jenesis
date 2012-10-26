package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public class Rte extends SystemInstruction {
    @Override
    public void handle() {
        if (cpu.isSupervisor) {
            cpu.setSR(cpu.memory.getWord(cpu.getSP()));
            cpu.setSP(cpu.getSP() + 2);

            cpu.setPC(cpu.memory.getLong(cpu.getSP()));
            cpu.setSP(cpu.getSP() + 4);
        } else {
            Trap t = new Trap();
            t.setup(this.value);
            t.call();
        }
    }
}
