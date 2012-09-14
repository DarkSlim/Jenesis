package com.wmbest.jenesis.m68k.instructions;

import jlibs.core.lang.Ansi;
import com.wmbest.jenesis.m68k.*;

public class Bcc extends QuickAndBranchInstruction {

    int condition = 0;
    long displacement = 0;

    public void setup(int value) {
        super.setup(value);
        size = BYTE;
        condition = (value >> 8) % 0xf;
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

        if (conditionCheck()) {
            cpu.setPC(cpu.getPC() + displacement);
        }
    }

    private boolean conditionCheck() {
        switch (condition) {
            case 2:
                return cpu.hi();
            case 3:
                return cpu.ls();
            case 4:
                return cpu.cc();
            case 5:
                return cpu.cs();
            case 6:
                return cpu.ne();
            case 7:
                return cpu.eq();
            case 8:
                return cpu.vc();
            case 9:
                return cpu.vs();
            case 10:
                return cpu.pl();
            case 11:
                return cpu.mi();
            case 12:
                return cpu.ge();
            case 13:
                return cpu.lt();
            case 14:
                return cpu.gt();
            case 15:
                return cpu.le();
        }
        return false;
    }
}
