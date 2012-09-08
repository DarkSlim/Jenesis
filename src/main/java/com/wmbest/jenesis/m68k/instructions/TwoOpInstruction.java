package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class TwoOpInstruction extends SEAInstruction {
    
    @Override
    public void setup(int value) {
        super.setup(value);

        int secondRegister = (value >> (OP_SHIFT + MODE_SHIFT)) & 0x7;
        int secondMode = (value >> OP_SHIFT) & 0x7;

        Operand op = new Operand();
        op.mode = secondMode;
        op.reg = secondRegister;
        op.cpu = cpu;
        operands[1] = op;
    }

    public String toString() {
        return super.toString();
    }
}
