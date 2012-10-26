package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class QuickAndBranchInstruction extends TwoOpInstruction {
    
    public static QuickAndBranchInstruction getInstruction(int value) {

        int first = (value >> 12) & 0xf;

        if (first == 5) {
            if (checkBits(value, 0x64)) {
                return new DBcc();
            } else if (checkBits(value, 0x60)) {
                return new Scc();
            } else if (checkBits(value, 0x10)) {
                return new AddQ();
            } else {
                return new SubQ();
            }
        } else if (first == 6) {
            int secondByte = (value >> 8) & 0xf;
            if (secondByte == 0x0) {
                return new Bra();
            } else if (secondByte == 0x1) {
                return new Bsr();
            } else {
                return new Bcc();
            }
        } else if (first == 7) {
            return new MoveQ();
        }

        throw new UnsupportedOpcodeException(value);
    }

    @Override
    public void preHandle() {
        super.preHandle();

        operands[0].disable = true;
        operands[1].disable = true;
    }
}
