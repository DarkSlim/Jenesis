package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class QuickAndBranchInstruction extends SEAInstruction {
    
    public static QuickAndBranchInstruction getInstruction(int value) {


        if (first == 5) {
            if (checkBit(value, 0x64)) {
                /** \todo DBcc */
            } else if (checkBit(value, 0x60)) {
                /** \todo Scc */
            } else if (checkBit(value, 0x10)) {
                result = new AddQ();
            } else {
                /** \todo SUBQ */
            }
        } else if (first == 6) {
            int secondByte = (value >> 12) & 0xf;
            if (secondByte == 0x0) {
                /** \todo BRA */
            } else if (secondByte == 0x1) {
                /** \todo BSR */
            } else {
                /** \todo Bcc */
            }
        } else if (first == 7) {
            /** \todo MOVEQ */
        }

        throw new UnsupportedOpcodeException(value);
    }
}
