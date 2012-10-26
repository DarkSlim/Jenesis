package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public class Reset extends SystemInstruction {

    @Override
    public void handle() {
        if (cpu.isSupervisor) {
            // TODO WHAT TO DO HERE?!??!?!?!
        } else {
            Trap t = new Trap();
            t.setup(this.value);
            t.call();
        }
    }
}
