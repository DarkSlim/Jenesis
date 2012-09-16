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

        Debugger debug = new Debugger(mCPU, mMem);
        debug.show();
    }
}
