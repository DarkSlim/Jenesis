package com.wmbest.jenesis.m68k;

import jlibs.core.lang.Ansi;

import com.wmbest.jenesis.m68k.instructions.*;
import com.wmbest.jenesis.memory.*;

public class SixtyEightK {

    public static final int BYTE_MASK     = 0x000000ff;
    public static final int NOT_BYTE_MASK = 0xffffff00;
    public static final int WORD_MASK     = 0x0000ffff;
    public static final int NOT_WORD_MASK = 0xffff0000;

    public static final long Z_MASK = 0x4;

    private long[] mDRegisters = new long[8];
    private long[] mARegisters = new long[8];

    private long mPC;
    private int mSR;

    public Memory memory;
    private Instruction mCurrentInst = null;

    public SixtyEightK(Memory aMem) {
        memory = aMem;
    }

    public void run() {
        while (memory.get((int)getPC()) != 0) {
            tick();
        }
    }

    public void tick() {
        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.GREEN, Ansi.Color.BLACK);
        ansi.out(toString());
        if (mCurrentInst == null || mCurrentInst.cost == 0) {
            mCurrentInst = Instruction.getInstruction(this, memory.get((int)mPC));

            mCurrentInst.call();
            mPC += 2;
        }
        mCurrentInst.cost--;
    }

    public long getPC() {
        return mPC;
    }

    public long incrPC() {
        mPC += 2;  // We need to keep this word aligned even though memory is index on the byte
        return mPC;
    }

    public void setPC(long aPC) {
        mPC = aPC;
    }

    public long getDx(long x) {
        return mDRegisters[(int) x];
    }

    public void setDx(long x, long val) {
        mDRegisters[(int) x] = val;
    }

    public long getAx(long x) {
        return mARegisters[(int) x];
    }

    public void setAx(long x, long val) {
        mARegisters[(int) x] = val;
    }

    public int getCCR() {
        return (int)(mSR & 0x1f);
    }

    public void setCCR(int val) {
        mSR = (mSR & 0xffe) & (val & 0x1f);
    }

    public boolean C() {
        return (mSR & 0x1) == 1;
    }

    public boolean V() {
        return ((mSR >> 1) & 0x1) == 1;
    }

    public boolean Z() {
        return ((mSR >> 2) & 0x1) == 1;
    }

    public boolean N() {
        return ((mSR >> 3) & 0x1) == 1;
    }

    public boolean X() {
        return ((mSR >> 4) & 0x1) == 1;
    }

    public void setZ(boolean value) {
        if (value) {
            mSR |= Z_MASK;
        } else {
            mSR &= ~Z_MASK;
        }
    }

    public boolean hi() {
        return !C() && !Z();
    }

    public boolean ls() {
        return C() || Z();
    }

    public boolean cc() {
        return !C();
    }

    public boolean cs() {
        return C();
    }

    public boolean ne() {
        return Z();
    }

    public boolean eq() {
        return !Z();
    }

    public boolean vc() {
        return !V();
    }

    public boolean vs() {
        return V();
    }

    public boolean pl() {
        return !N();
    }

    public boolean mi() {
        return N();
    }

    public boolean ge() {
        return (N() && V()) || (!N() && !V());
    }

    public boolean lt() {
        return !ge();
    }

    public boolean le() {
        return Z() || (!N() && V()) || (!V() && N());
    }

    public boolean gt() {
        return !le();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("++++++++++++++++++++    68000    +++++++++++++++++++++\n");
        builder.append("    PC:  0x" + Long.toHexString(mPC) + "\n");
        builder.append("    D[0-7]: (");
        for (int i = 0; i < 8; ++i) {
            builder.append("0x" + Long.toHexString(mDRegisters[i]));

            if (i != 7) 
                builder.append(",");
        }
        builder.append(")\n");

        builder.append("    A[0-7]: (");
        for (int i = 0; i < 8; ++i) {
            builder.append("0x" + Long.toHexString(mARegisters[i]));

            if (i != 7) 
                builder.append(",");
        }
        builder.append(")\n");
        builder.append("++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        return builder.toString();
    }
}
