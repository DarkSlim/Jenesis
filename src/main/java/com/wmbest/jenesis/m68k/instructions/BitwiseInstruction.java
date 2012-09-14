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
                return new Btst();
            case 0x1:
                return new Bchg();
            case 0x2:
                return new Bclr();
            case 0x3:
                return new Bset();
        }

        throw new UnsupportedOpcodeException(value);
    }
}
