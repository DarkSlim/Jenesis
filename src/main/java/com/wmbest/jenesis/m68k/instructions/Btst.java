package com.wmbest.jenesis.m68k.instructions;

import jlibs.core.lang.Ansi;
import com.wmbest.jenesis.m68k.*;

public class Btst extends BitwiseInstruction {

    int type = 0;
    long bit = 0;

    public void setup(int value) {
        super.setup(value);
        type = value >> 6;

        if (type == 0x22) {
            size = BYTE;
            bit = operands[0].immediateWord();
        } else {
            size = LONG;
            bit = cpu.getDx(type >> 3);
        }
    }

    @Override
    public void handle() {
        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.CYAN, Ansi.Color.BLACK);
        ansi.outln(toString());

        boolean notZero = (operands[0].getVal() & bit) == bit;
        cpu.setZ(!notZero);
    }
}
