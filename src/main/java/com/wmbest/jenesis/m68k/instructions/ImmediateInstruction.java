package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class ImmediateInstruction extends TwoOpInstruction {
    
    public static ImmediateInstruction getInstruction(int value) {

        // 8 bit constant functions
        int secondByte = (value & 0x0f00) >> 8;

        switch(secondByte) {
            case 0x0:
                /** \todo ORI to CCR (size 0b00, ea MODE 0b111) */
                /** \todo ORI to SR (size 0b01, ea MODE 0b111) */
                /** \todo ORI */
            case 0x2:
                return AndI.getInstruction(value);
            case 0x4:
                return new SubI();
            case 0x6:
                return new AddI();
            case 0xa:
                /** \todo EORI to CCR (size 0b00, ea MODE 0b111) */
                /** \todo EORI to SR (size 0b01, ea MODE 0b111) */
                /** \todo EORI */
            case 0xc:
                /** \todo CMP */
        }

        throw new UnsupportedOpcodeException(value);
    }
}
