package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Bsr extends QuickAndBranchInstruction {

    long displacement = 0;

    public void setup(int value) {
        super.setup(value);

        displacement = value & 0xff;

        if (displacement == 0x00) {
            displacement = operands[0].immediateWord();
        } else if (displacement == 0xff) {
            // This will  only happen in 68020, 30, and 40
            displacement = operands[0].immediateLong();
        }
    }

    @Override
    public void handle() {
        //Push SP 
        cpu.setSP(cpu.getSP() - 4);

        long nextInstruction = cpu.getPC();
        cpu.memory.set((int) cpu.getSP(), (int)((nextInstruction >> 16) & 0xffff));
        cpu.memory.set((int) cpu.getSP() + 2, (int)(nextInstruction & 0xffff));

        cpu.setPC(cpu.getPC() + displacement);
    }
}
