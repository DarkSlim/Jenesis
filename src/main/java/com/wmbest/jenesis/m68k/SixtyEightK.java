package com.wmbest.jenesis.m68k;

import jlibs.core.lang.Ansi;

import com.wmbest.jenesis.m68k.instructions.*;
import com.wmbest.jenesis.util.*;

public class SixtyEightK {

    public static final int BYTE_MASK     = 0x000000ff;
    public static final int NOT_BYTE_MASK = 0xffffff00;
    public static final int WORD_MASK     = 0x0000ffff;
    public static final int NOT_WORD_MASK = 0xffff0000;

    private long[] mDRegisters = new long[8];
    private long[] mARegisters = new long[8];

    private long mPC;
    private int mSR;

    public int[] memory;
    private Instruction mCurrentInst = null;

    public SixtyEightK(int[] aMem) {
        memory = aMem;
    }

    public void run() {
        while (memory[(int)getPC()] != 0) {
            tick();
        }
    }

    public void tick() {
        Ansi ansi = new Ansi(Ansi.Attribute.NORMAL, Ansi.Color.GREEN, Ansi.Color.BLACK);
        ansi.out(toString());
        if (mCurrentInst == null || mCurrentInst.cost == 0) {
            mCurrentInst = Instruction.getInstruction(this, memory[(int)mPC]);

            mCurrentInst.call();
            mPC++;
        }
        mCurrentInst.cost--;
    }

    public long getPC() {
        return mPC;
    }

    public long incrPC() {
        return ++mPC;
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

    public int ccr() {
        return (int)(mSR & 0x1f);
    }

    public void writeByte(int address, int value) {
        int inMem = memory[address];
        inMem = inMem & NOT_BYTE_MASK;
        inMem |= (value & BYTE_MASK);
        memory[address] = inMem;
    }

    public void writeWord(int address, int value) {
        int inMem = memory[address];
        inMem = inMem & NOT_WORD_MASK;
        inMem |= (value & WORD_MASK);
        memory[address] = inMem;
    }

    public void writeLong(int address, long value) {
        writeWord(address, (int) (value >> 16));
        writeWord(address + 1, (int)(value & WORD_MASK));
    }

    public int readByte(int address) {
        return -1;
    }

    public int readWord(int address) {
        return -1;
    }

    public long readLong(int address) {
        return -1;
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
