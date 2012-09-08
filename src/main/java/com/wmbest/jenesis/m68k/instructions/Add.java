package com.wmbest.jenesis.m68k.instructions;

import jlibs.core.lang.Ansi;
import com.wmbest.jenesis.m68k.*;

public class Add extends TwoOpInstruction {

    int size;

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
        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.CYAN, Ansi.Color.BLACK);
        ansi.outln(toString());
        ansi.outln("Second Type: 0b" + Integer.toBinaryString(operands[1].mode));
        ansi.outln("Second Register: 0b" + Integer.toBinaryString(operands[1].reg));
        ansi.outln("Size: " + SIZE_STRING[size] + "\n");

        if (operands[1].mode < 3) {
            cpu.setDx(operands[1].reg, operands[0].getVal() + cpu.getDx(operands[1].reg));
        } else if (operands[1].mode == 3 || operands[1].mode == 7) {
            cpu.setAx(operands[1].reg, operands[0].getVal() + cpu.getAx(operands[1].reg));
        } else {
            operands[0].setVal(operands[0].getVal() + cpu.getDx(operands[1].reg));
        }
    }

    @Override
    public String disassemble() {
        return "ADD";
    }
}
