package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Lea extends SystemInstruction {

    public void setup(int value) {
        super.setup(value);
        size = LONG;

        operands[0].disable = false;
    }

    @Override
    public void handle() {
        cpu.setAx(((int) value >> 9) & 0x7, operands[0].getVal());
    }

    public String disassemble() {
        return super.disassemble() + ", A" + (((int) value >> 9) & 0x7);
    }
}
