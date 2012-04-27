package com.wmbest.jenesis;

import com.wmbest.jenesis.hardware.*;
import com.wmbest.jenesis.util.*;


public class Main {

    private static UInt16[] mMem = new UInt16[0xffffff];
    private static SixtyEightK mCPU;

    public static void main( String[] args ) {
        mMem[0] = new UInt16();
        mMem[0].set(0x0003);
        mMem[1] = new UInt16();
        mMem[1].set(0x0000);

        mCPU = new SixtyEightK(mMem);
        mCPU.tick();
    }
}
