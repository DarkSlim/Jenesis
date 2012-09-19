package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Tst extends SystemInstruction {

    private long CHECK_NEG = 0x80;

    public void setup(int value) {
        super.setup(value);

        size = (value >> 6) & 0xff;

        if (size < 3) {
            name = "TST";
        } else {
            name = "TAS";
        }
    }

    @Override
    public void handle() {
        switch(size) {
            case 0:
                check(CHECK_NEG);
                break;
            case 1:
                check(CHECK_NEG << 8);
                break;
            case 2:
                check(CHECK_NEG << 24);
                break;
            case 3:
                check(CHECK_NEG);
                break;
        }
    }

    private void check(long mask) {
        cpu.setN((operands[0].getVal() & mask) == mask);
        cpu.setZ(operands[0].getVal() == 0);

        if (size == 0x3) {
            operands[0].setVal(operands[0].getVal() | CHECK_NEG);
        }
    }
}
