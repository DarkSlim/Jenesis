package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class EORItoCCR extends EorI {

    public void setup(int value) {
        super.setup(value);
        size = BYTE;
    }

    @Override
    public void handle() {
        cpu.setCCR(cpu.getCCR() ^ operands[1].immediateByte());
    }
}
