package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class ImmediateInstruction extends TwoOpInstruction {
    protected long data;
    
    public static ImmediateInstruction getInstruction(int value) {

        // 8 bit constant functions
        int secondByte = (value & 0x0f00) >> 8;

        switch(secondByte) {
            case 0x0:
                return OrI.getInstruction(value);
            case 0x2:
                return AndI.getInstruction(value);
            case 0x4:
                return new SubI();
            case 0x6:
                return new AddI();
            case 0xa:
                return EorI.getInstruction(value);
            case 0xc:
                throw new UnsupportedOpcodeException(value);
                /** \todo CMP */
        }
        return null;
    }

    @Override
    public void setup(int value) {
        super.setup(value);

        operands[1].disable = true;
    }

    @Override
    public void preHandle() {
        super.preHandle();

        if (size == BYTE) {
            data = operands[0].immediateByte();
        } else if (size == WORD) {
            data = operands[0].immediateWord();
        } else {
            data = operands[0].immediateLong();
        }
    }

    @Override
    public String disassemble() {
        return name + "\t#$" + Long.toHexString(data) + ", " + operands[0].toString();
    }
}
