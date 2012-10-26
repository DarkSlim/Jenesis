package com.wmbest.jenesis.m68k.instructions;

public abstract class MulAndInstruction extends SEAInstruction {
    
    public static MulAndInstruction getInstruction(int value) {
        int mode1 = (value >> 3) % 0x7;
        int mode2 = (value >> 6) % 0x7;

        switch(mode2) {
            case 3:
                //return new MulU();
            case 7:
                //return new MulS();
            default:
                if (mode2 == 4 && mode1 <= 1) {
                    return new Abcd();
                } else if (true /** \todo something here*/) {
                    //return new Exg();
                }
                return new And();
        }
    }
}
