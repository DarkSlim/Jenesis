package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class AddI extends ImmediateInstruction {

    public void setup(int value) {
        super.setup(value);

        name = "ADDI";
        size = getSize();

        for( int i = 0; i < operands.length; ++i) {
            if (operands[i] != null) {
                operands[i].size = size;
            }
        }
    }

    private int getSize() {
        switch (operands[1].mode) {
            case 0:
                name += ".B";
                return BYTE;
            case 1:
                name += ".W";
                return WORD;
            case 2:
                name += ".L";
                return LONG;
        }
        return -1;
    }

    @Override
    public void handle() {
        operands[0].setVal(operands[0].getVal() + data);
    }
}
