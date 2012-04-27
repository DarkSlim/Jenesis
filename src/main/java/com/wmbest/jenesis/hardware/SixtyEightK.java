package com.wmbest.jenesis.hardware;

import com.wmbest.jenesis.util.*;

public class SixtyEightK {
    private UInt32[] mDRegisters = new UInt32[8];
    private UInt32[] mARegisters = new UInt32[8];
    private UInt16 mPC = new UInt16();
    private UInt16 mSR = new UInt16();

    private UInt16[] mMemory;
    private Instruction mCurrentInst = null;

    public SixtyEightK(UInt16[] aMem) {
        mMemory = aMem;
    }

    public void tick() {
        if (mCurrentInst == null || mCurrentInst.cost == 0) {
            mCurrentInst = nextInstruction();
            handleInstruction(mCurrentInst);
            System.out.println("Handled opcode " + String.valueOf(mCurrentInst.word1.get() & 0xf0000000));
        }
        mCurrentInst.cost--;
    }

    private long ccr() {
        return mSR.get() & 0x1f;
    }

    private void handleInstruction(Instruction aInst) {
        int firstOp = aInst.word1.get() & 0xf000;
        if (firstOp == 0x0) {
        }
    }

    private Instruction nextInstruction() {
        Instruction inst = new Instruction();
        inst.word1 = mMemory[mPC.get()];
        inst.word2 = mMemory[mPC.incr()];
        return inst;
    }

    private long immediate(int aInst, int aSize) {
        return 0L;
    }

    public class Instruction {
        public UInt16 word1;
        public UInt16 word2;
        public int cost;
    }
}
