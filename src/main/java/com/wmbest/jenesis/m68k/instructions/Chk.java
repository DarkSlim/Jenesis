package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public class Chk extends SystemInstruction {

    int r = 0;
    
    public void setup(int value) {
        super.setup(value);

        //Check size for MC68020 and above
        size = WORD;
        operands[0].disable = false;
        r = (value >> 8) & 0x7;
    }

    @Override
    public void handle() {
        int val = (int) cpu.getDx(r);
        if (val < 0) {
            cpu.setN(true);
            trap();
        } else if (val > operands[0].getVal()) {
            cpu.setN(false);
            trap();
        }
    }

    private void trap() {
        // TODO implement trap with vector 6
    }

    @Override
    public String disassemble() {
        return super.disassemble() + ", D" + r;
    }
}
