package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class QuickAndBranchInstruction extends TwoOpInstruction {
    
    public static QuickAndBranchInstruction getInstruction(int value) {

        int first = (value >> 12) & 0xf;

        if (first == 5) {
            if (checkBits(value, 0x64)) {
                /** \todo DBcc */
            } else if (checkBits(value, 0x60)) {
                /** \todo Scc */
            } else if (checkBits(value, 0x10)) {
                return new AddQ();
            } else {
                return new SubQ();
            }
        } else if (first == 6) {
            int secondByte = (value >> 8) & 0xf;
            if (secondByte == 0x0) {
                /** \todo BRA */
            } else if (secondByte == 0x1) {
                /** \todo BSR */
            } else {
                return new Bcc();
            }
        } else if (first == 7) {
            /** \todo MOVEQ */
        }

        throw new UnsupportedOpcodeException(value);
    }
}
