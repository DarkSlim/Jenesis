package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class AndI extends ImmediateInstruction {
 
    public static AndI getInstruction(int value) {
        if (value == 0x023c) {
            return new ANDItoCCR();
        } else if (value == 0x027c) {
            /** \todo ANDItoSR */
        }
        return new AndI();
    }

    public void setup(int value) {
        super.setup(value);

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
                return BYTE;
            case 1:
                return WORD;
            case 2:
                return LONG;
        }
        return -1;
    }

    @Override
    public void handle() {
        operands[0].setVal(operands[0].getVal() & data);
    }
}
