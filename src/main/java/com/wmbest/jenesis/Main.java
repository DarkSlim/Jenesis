package com.wmbest.jenesis;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

import jlibs.core.lang.Ansi;

public class Main {

    private static int[] mMem = new int[0xffffff];
    private static SixtyEightK mCPU;

    public static void main( String[] args ) {
        mMem[0] = 0x367C;
        mMem[1] = 0x0063;
        mMem[2] = 0x384B;
        mMem[3] = 0x38CC;
        mMem[4] = 0xD8CB;
        mMem[5] = 0x0681;
        mMem[6] = 0xFFFF;
        mMem[7] = 0xCCCC;
        mMem[8] = 0x2601;
        mMem[9] = 0x0483;
        mMem[10] = 0x0F0F;
        mMem[11] = 0x0C0C;
        mMem[12] = 0x387C;
        mMem[13] = 0x0100;
        mMem[14] = 0x38C9;


        mCPU = new SixtyEightK(mMem);
        mCPU.run();

        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.GREEN, Ansi.Color.BLACK);
        ansi.out(mCPU.toString());
    }
}
