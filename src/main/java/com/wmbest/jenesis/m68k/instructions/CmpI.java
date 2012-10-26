package com.wmbest.jenesis.m68k.instructions;

public class CmpI extends ImmediateInstruction {

    @Override
    public void setup(int value) {
        super.setup(value);
        name = "CmpI";
        size = getSize((value >> 6) & 0x7);
        operands[0].size = size;
    }

    public int getSize(int size) {
        switch(size) {
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
        long val = operands[0].getVal() - data;
        if (val < 0) {
            cpu.setN(true);
            cpu.setV(cpu.C() ^ true);
            cpu.setC(true);
        } else if (val == 0) {
            cpu.setZ(true);
        }
    }

    @Override
    public String disassemble() {
        return "CMPI" + SIZE_ABBVR[size] + "\t#" + data + ", " + operands[0].toString();
    }
}
