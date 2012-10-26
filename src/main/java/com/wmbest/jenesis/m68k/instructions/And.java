package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class And extends MulAndInstruction {

    int size;

    public void setup(int value) {
        super.setup(value);

        name = "AND";
        size = getSize();

        for( int i = 0; i < operands.length; ++i) {
            if (operands[i] != null) {
                operands[i].size = size;
            }
        }

        operands[1].disable = true;
    }

    private int getSize() {
        switch (operands[1].mode) {
            case 0:
            case 4:
                return BYTE;
            case 1:
            case 3:
            case 5:
                return WORD;
            case 2:
            case 6:
            case 7:
                return LONG;
        }
        return -1;
    }

    @Override
    public void handle() {
        if (operands[1].mode > 2) {
            cpu.setDx(operands[1].reg, cpu.getDx(operands[1].reg) & operands[0].getVal());
        } else {
            operands[0].setVal(operands[0].getVal() & cpu.getDx(operands[1].reg));
        }
    }
}
