package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Nop extends SystemInstruction {

    public void setup(int value) {
        super.setup(value);
        name = "NOP";
        operands[0].disable = true;  // DONT DO ANYTHING WITH THE OPERAND
    }

    @Override
    public void handle() {

    }

    @Override
    public String disassemble() {
        return name;
    }
}

