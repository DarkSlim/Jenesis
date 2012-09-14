package com.wmbest.jenesis.m68k.instructions;

import jlibs.core.lang.Ansi;
import com.wmbest.jenesis.m68k.*;

public class ORItoCCR extends OrI {

    public void setup(int value) {
        super.setup(value);
        size = BYTE;
    }

    @Override
    public void handle() {
        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.CYAN, Ansi.Color.BLACK);
        ansi.outln(toString());

        cpu.setCCR(cpu.getCCR() | operands[1].immediateByte());
    }
}
