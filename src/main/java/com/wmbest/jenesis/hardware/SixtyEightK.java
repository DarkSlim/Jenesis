package com.wmbest.jenesis.hardware;

import com.wmbest.jenesis.util.UInt32;

public class SixtyEightK {
    private UInt32[] mDRegisters = new UInt32[8];
    private UInt32[] mARegisters = new UInt32[8];
    private UInt32 PC = new UInt32();
    private UInt32 SR = new UInt32();

    private byte ccr() {
        return ((byte) SR.get()) & 0x1fb;
    }

    private Memory mMemory;

}
