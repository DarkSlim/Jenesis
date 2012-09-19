package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Lea extends SystemInstruction {

    public void setup(int value) {
        super.setup(value);
        name = "LEA.L";
        size = LONG;
    }

    @Override
    public void handle() {
        cpu.setAx(((int) value >> 9) & 0x7, operands[0].getVal());
    }
}
