package com.wmbest.jenesis.m68k.instructions;

import jlibs.core.lang.Ansi;
import com.wmbest.jenesis.m68k.*;

public class SubI extends ImmediateInstruction {

    public void setup(int value) {
        super.setup(value);

        name = "SUBI";
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
        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.CYAN, Ansi.Color.BLACK);
        ansi.outln(toString());
        ansi.outln("Second Type: 0b" + Integer.toBinaryString(operands[1].mode));
        ansi.outln("Second Register: 0b" + Integer.toBinaryString(operands[1].reg));
        ansi.outln("Size: " + SIZE_STRING[size] + "\n");

        if (size == BYTE) {
            operands[0].setVal(operands[0].getVal() - operands[1].immediateByte());
        } else if (size == WORD) {
            operands[0].setVal(operands[0].getVal() - operands[1].immediateWord());
        } else {
            operands[0].setVal(operands[0].getVal() - operands[1].immediateLong());
        }
    }

    @Override
    public String disassemble() {
        return "SUBI";
    }
}
