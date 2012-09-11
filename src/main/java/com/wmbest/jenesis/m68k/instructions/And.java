package com.wmbest.jenesis.m68k.instructions;

import jlibs.core.lang.Ansi;
import com.wmbest.jenesis.m68k.*;

public class And extends TwoOpInstruction {

    int size;

    public void setup(int value) {
        super.setup(value);

        name = "AND";
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
    }

    @Override
    public String disassemble() {
        return "AND";
    }
}
