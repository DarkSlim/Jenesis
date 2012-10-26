package com.wmbest.jenesis.m68k.instructions;

public abstract class DivOrInstruction extends SEAInstruction {
    
    public static DivOrInstruction getInstruction(int value) {
        int mode1 = (value >> 3) % 0x7;
        int mode2 = (value >> 6) % 0x7;

        switch(mode2) {
            case 3:
                //return new DivU();
            case 7:
                //return new DivS();
            default:
                //if (mode2 == 4 && mode1 <= 1) {
                    //return new Sbcd();
                //}
                //return new Or();
        }
        return null;
    }
}
