package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Move extends TwoOpInstruction {

    public void setup(int value) {
        super.setup(value);

        name = "MOVE";
        size = (0xf000 & value) >> 12;

        for( int i = 0; i < operands.length; ++i) {
            if (operands[i] != null) {
                operands[i].size = size;
            }
        }
    }

    @Override
    public void handle() {
        operands[1].setVal(operands[0].getVal());
    }
}
