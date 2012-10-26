package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public class Stop extends SystemInstruction {
    @Override
    public void handle() {
        if (cpu.isSupervisor) {
            int data = operands[0].immediateWord();
            cpu.setSP(data);
            cpu.setUSP(data);
            cpu.breakPoint = true;
        } else {
            Trap t = new Trap();
            t.setup(this.value);
            t.call();
        }
    }
}
