package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class DBcc extends QuickAndBranchInstruction {

    int condition = 0;
    long displacement = 0;

    public void setup(int value) {
        super.setup(value);
        size = BYTE;
        condition = (value >> 8) % 0xf;
        operands[0].size = size;

        displacement = operands[0].immediateWord();
    }

    @Override
    public void handle() {
        if (!conditionCheck()) {
            long dn = cpu.getDx(operands[0].reg);
            dn--;
            if (dn >= 0) {
                cpu.setPC(cpu.getPC() + displacement);
            }
        }
    }

    private boolean conditionCheck() {
        switch (condition) {
            case 0:
                return false;
            case 1:
                return true;
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
