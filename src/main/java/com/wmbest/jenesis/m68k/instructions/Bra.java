package com.wmbest.jenesis.m68k.instructions;

import jlibs.core.lang.Ansi;
import com.wmbest.jenesis.m68k.*;

public class Bra extends QuickAndBranchInstruction {

    long displacement = 0;

    public void setup(int value) {
        super.setup(value);

        displacement = value & 0xff;

        if (displacement == 0x00) {
            displacement = operands[0].immediateWord();
        } else if (displacement == 0xff) {
            // This will  only happen in 68020, 30, and 40
            displacement = operands[0].immediateLong();
        }
    }

    @Override
    public void handle() {
        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.CYAN, Ansi.Color.BLACK);
        ansi.outln(toString());

        cpu.setPC(cpu.getPC() + displacement);
    }
}
