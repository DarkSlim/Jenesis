package com.wmbest.jenesis;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.memory.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

import jlibs.core.lang.Ansi;

public class Main {

    private static Memory mMem = new Memory();
    private static SixtyEightK mCPU;

    private Label mCPUData;

    public static void main( String[] args ) {

        mCPU = new SixtyEightK(mMem);

        mMem.put(0x367C);
        mMem.put(0x0063);
        mMem.put(0x384B);
        mMem.put(0x38CC);
        mMem.put(0xD8CB);
        mMem.put(0x0681);
        mMem.put(0xFFFF);
        mMem.put(0xCCCC);
        mMem.put(0x2601);
        mMem.put(0x0483);
        mMem.put(0x0F0F);
        mMem.put(0x0C0C);
        mMem.put(0x387C);
        mMem.put(0x0100);
        mMem.put(0x38C9);

        Debugger debug = new Debugger(mCPU, mMem);
        debug.show();
    }
}
