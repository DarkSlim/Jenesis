package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Move extends TwoOpInstruction {

    public void setup(int value) {
        super.setup(value);

        name = "MOVE";
        int s = (0xf000 & value) >> 12;
        size = getSize(s);

        for( int i = 0; i < operands.length; ++i) {
            if (operands[i] != null) {
                operands[i].size = size;
            }
        }
    }

    private int getSize(int s) {
        switch (s) {
            case BYTE:
                name += ".B";
                return BYTE;
            case WORD:
                name += ".W";
                return WORD;
            case LONG:
                name += ".L";
                return LONG;
        }
        return -1;
    }

    @Override
    public void handle() {
        operands[1].setVal(operands[0].getVal());
    }
}
