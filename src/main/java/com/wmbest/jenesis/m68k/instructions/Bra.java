package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Bra extends QuickAndBranchInstruction {

    long displacement = 0;

    public void setup(int value) {
        super.setup(value);

        name = "BRA";
        displacement = (byte) (value & 0xff);

        operands[0].disable = true;
        operands[1].disable = true;

        if (displacement == 0x00) {
            displacement = operands[0].immediateWord();
        } else if (displacement == 0xff) {
            // This will  only happen in 68020, 30, and 40
            displacement = operands[0].immediateLong();
        }
        displacement += 2;
    }

    @Override
    public void handle() {
        cpu.setPC(cpu.getPC() + displacement);
    }

    @Override
    public String disassemble() {
        return name + "\t$" + Long.toHexString(cpu.getPC() + displacement);
    }
}
