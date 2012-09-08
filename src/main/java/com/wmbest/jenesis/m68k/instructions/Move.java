package com.wmbest.jenesis.m68k.instructions;

import jlibs.core.lang.Ansi;
import com.wmbest.jenesis.m68k.*;

public class Move extends TwoOpInstruction {

    int size;

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
        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.CYAN, Ansi.Color.BLACK);
        ansi.outln(toString());
        ansi.outln("Second Type: 0b" + Integer.toBinaryString(operands[1].mode));
        ansi.outln("Second Register: 0b" + Integer.toBinaryString(operands[1].reg));
        ansi.outln("Size: " + SIZE_STRING[size] + "\n");

        operands[1].setVal(operands[0].getVal());
    }

    @Override
    public String disassemble() {
        //return "MOVE " + firstOp() + ", " + secondOp() + "\n";
        return "MOVE";
    }
}
