package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Btst extends BitwiseInstruction {

    int type = 0;
    long bit = 0;

    public void setup(int value) {
        super.setup(value);
        type = value >> 6;

        if (type == 0x22) {
            size = BYTE;
            bit = operands[0].immediateWord();
        } else {
            size = LONG;
            bit = cpu.getDx(type >> 3);
        }
    }

    @Override
    public void handle() {
        boolean notZero = (operands[0].getVal() & bit) == bit;
        cpu.setZ(!notZero);
    }
}
