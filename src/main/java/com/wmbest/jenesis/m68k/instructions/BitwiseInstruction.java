package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class BitwiseInstruction extends SEAInstruction {
    
    public static BitwiseInstruction getInstruction(int value) {

        // 8 bit constant functions
        int cmd = (value  >> 6) & 0x07;
        int movep = (value >> 3) & 0x07;

        if (movep == 1 && cmd >= 4) {
            /** \todo MOVEP */
        }

        switch(cmd & 0xff) {
            case 0x0:
                /** \todo BTST */
            case 0x1:
                /** \todo BCHG */
            case 0x2:
                /** \todo BCLR */
            case 0x3:
                /** \todo BSET */
        }

        throw new UnsupportedOpcodeException(value);
    }
}
