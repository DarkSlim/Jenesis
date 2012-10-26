package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class MoveQ extends QuickAndBranchInstruction {

    int data = 0;

    public void setup(int value) {
        super.setup(value);

        name = "MOVEQ";
        size = LONG;

        data = 0x3f & value;

        operands[0].disable = false;
    }

    @Override
    public void handle() {
        cpu.setDx(operands[1].reg, data);
    }

    @Override
    public String disassemble() {
        return "MOVEQ\t#" + data + ", D" + operands[1].reg;
    }
}
