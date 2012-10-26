package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class AddQ extends QuickAndBranchInstruction {

    public void setup(int value) {
        super.setup(value);

        name = "ADDQ";
        size = getSize();

        operands[0].disable = false;

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
        operands[0].setVal(operands[0].getVal() + operands[1].reg);
    }

    @Override
    public String disassemble() {
        return "ADDQ" + SIZE_ABBVR[size] + "\t#" + operands[1].reg + ", " + operands[0].toString();
    }
}
