package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Add extends TwoOpInstruction {

    public void setup(int value) {
        super.setup(value);

        name = "ADD";
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
        if (operands[1].mode > 4 && operands[1].mode < 7) {
            if (operands[0].mode == 0) {
                addDx(true);
                return;
            } else if (operands[0].mode == 1) {
                addAPreWithX();
                return;
            }
        }

        if (operands[1].mode < 3) {
            addDx(false);
        } else if (operands[1].mode == 3 || operands[1].mode == 7) {
            addA();
        } else {
            addEA();
        }
    }

    public void addDx(boolean x) {
        cpu.setDx(operands[1].reg, operands[0].getVal() +
            cpu.getDx(operands[1].reg) + ((x && cpu.X()) ? 1 : 0));
    }

    public void addA() {
        cpu.setAx(operands[1].reg, operands[0].getVal() + cpu.getAx(operands[1].reg));
    }

    public void addEA() {
        operands[0].setVal(operands[0].getVal() + cpu.getDx(operands[1].reg));
    }

    public void addAPreWithX() {
        cpu.setAx(operands[1].reg, operands[0].getVal() +
            cpu.getAx(operands[1].reg) + (cpu.X() ? 1 : 0));
        cpu.setAx(operands[0].reg, cpu.getAx(operands[0].reg) - sizeToByte(size));
        cpu.setAx(operands[1].reg, cpu.getAx(operands[1].reg) - sizeToByte(size));
    }
}
