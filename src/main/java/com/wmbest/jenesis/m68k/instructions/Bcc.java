package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Bcc extends QuickAndBranchInstruction {

    int condition = 0;
    long displacement = 0;

    public void setup(int value) {
        super.setup(value);
        size = BYTE;
        condition = (value >> 8) & 0xf;
        displacement = (byte) (value & 0xff);

        operands[0].disable = true;
        operands[1].disable = true;

        if (displacement == (byte) 0x00) {
            displacement = operands[0].immediateWord();
        } else if (displacement == (byte) 0xff) {
            // This will  only happen in 68020, 30, and 40
            displacement = operands[0].immediateLong();
        }
        displacement += 2;
    }

    @Override
    public void handle() {
        if (conditionCheck()) {
            cpu.setPC(cpu.getPC() + displacement - 2);
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

    @Override
    public String disassemble() {
        name = "BCC";
        switch (condition) {
            case 2:
                name = "BHI";
                break;
            case 3:
                name = "BLS";
                break;
            case 4:
                name = "BCC";
                break;
            case 5:
                name = "BCS";
                break;
            case 6:
                name = "BNE";
                break;
            case 7:
                name = "BEQ";
                break;
            case 8:
                name = "BVC";
                break;
            case 9:
                name = "BVS";
                break;
            case 10:
                name = "BPL";
                break;
            case 11:
                name = "BMI";
                break;
            case 12:
                name = "BGE";
                break;
            case 13:
                name = "BLT";
                break;
            case 14:
                name = "BGT";
                break;
            case 15:
                name = "BLE";
                break;
        }

        return name + "\t" + Long.toHexString(cpu.getPC() + displacement);
    }
}
