package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class MoveM extends SystemInstruction {

    int dr;
    int sz;
    int registerListMask;

    public void setup(int value) {
        super.setup(value);

        name = "MOVEM";
        sz = (value >> 6) & 0x1;
        dr = (value >> 10) & 0x1;

        size = (sz == 1) ? LONG : WORD;
        registerListMask = operands[0].immediateWord();
        if (operands[0].mode == 0x4) {
            registerListMask = (Integer.reverse(registerListMask) >> 16) & 0xffff;
        }
    }

    @Override
    public void handle() {
        int counter = 0;
        for (int i = 0; i < 16; i++) {
            int offset = counter * (2 + ((size == LONG) ? 2 : 0));
            if (((registerListMask >> i) & 0x1) == 1) {
                int ax = counter - 8;
                if (dr == 0) {
                    if (counter >= 8) {
                        if (size == LONG) {
                            cpu.memory.setLong((int) operands[0].getVal(), cpu.getAx(ax));
                        } else {                     
                            cpu.memory.setWord((int) operands[0].getVal(), (int) cpu.getAx(counter));
                        }
                    } else {
                        if (size == LONG) {
                            cpu.memory.setLong((int) operands[0].getVal(), cpu.getDx(ax));
                        } else {                     
                            cpu.memory.setWord((int) operands[0].getVal(), (int) cpu.getDx(counter));
                        }
                    }
                } else {
                    if (counter >= 8) {
                        if (size == LONG) {
                            cpu.setAx(ax, cpu.memory.getLong((int) operands[0].getVal()));
                        } else {                                   
                            cpu.setAx(ax, cpu.memory.getWord((int) operands[0].getVal()));
                        }
                    } else {
                        if (size == LONG) {
                            cpu.setDx(counter, cpu.memory.getLong((int) operands[0].getVal()));
                        } else {
                            cpu.setDx(counter, cpu.memory.getWord((int) operands[0].getVal()));
                        }
                    }
                }
                counter++;
            }
        }
    }
}
