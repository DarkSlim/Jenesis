package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

/**
 * Most common Instruction type
 *
 * Defines basic behavior for Single Effective Address Instructions
 *
 * See http://emu-docs.org/CPU%2068k/68kpm.pdf section 2-1
 */

public abstract class SEAInstruction extends Instruction {
    
    public static final int ADDRESS_MASK = 0x7;
    public static final int MODE_SHIFT = 3;
    public static final int OP_SHIFT = 6;

    @Override
    public void setup(int value) {
        super.setup(value);

        int eaRegister = getEffectiveRegister();
        int eaMode = getEffectiveMode();

        Operand op = new Operand();
        op.mode = eaMode;
        op.reg = eaRegister;
        op.cpu = cpu;
        operands[0] = op;

    }

    public int getEffectiveRegister() {
        return getRegister(this.value);
    }

    public int getEffectiveMode() {
        return getMode(this.value);
    }

    public int getRegister(int val) {
        return val & ADDRESS_MASK;
    }

    public int getMode(int val) {
        val >>= MODE_SHIFT;
        return val & ADDRESS_MASK;
    }

    public String toString() {
        StringBuilder output = new StringBuilder(super.toString() + "\n");

        output.append("Source Type: 0b" + Integer.toBinaryString(operands[0].mode));
        output.append("\n");
        output.append("Source Register: 0b" + Integer.toBinaryString(operands[0].reg));

        return output.toString();
    }

    public String disassemble() {
        return name.toUpperCase() + "\t" + ((operands[0].disable) ? "" : operands[0].toString());
    }
}
