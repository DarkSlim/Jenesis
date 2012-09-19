package com.wmbest.jenesis.m68k;

import jlibs.core.lang.Ansi;

import com.wmbest.jenesis.m68k.instructions.*;
import com.wmbest.jenesis.memory.*;

public class SixtyEightK {

    public static final int BYTE_MASK     = 0x000000ff;
    public static final int NOT_BYTE_MASK = 0xffffff00;
    public static final int WORD_MASK     = 0x0000ffff;
    public static final int NOT_WORD_MASK = 0xffff0000;

    public static final long C_MASK = 0x1;
    public static final long V_MASK = 0x2;
    public static final long Z_MASK = 0x4;
    public static final long N_MASK = 0x8;
    public static final long X_MASK = 0x10;

    private long[] mDRegisters = new long[8];
    private long[] mARegisters = new long[7];

    private long mPC;
    private long mSSP;
    private long mUSP;

    private Boolean isSupervisor = new Boolean(false);

    private int mSR;

    public Memory memory;
    private Instruction mCurrentInst = null;
    private SixtyEightKListener mListener;

    private volatile Thread mBackgroundThread;
    private Boolean breakPoint = new Boolean(false);

    public SixtyEightK(Memory aMem) {
        memory = aMem;
    }

    public void setupProgram() {
        mSSP = (((long) memory.get(0x0)) << 16) | memory.get(0x2);
        mPC = (((long) memory.get(0x4)) << 16) | memory.get(0x6);

        if (mListener != null) {
            mListener.onTick();
        }
    }

    public boolean isRunning() {
        return mBackgroundThread != null;
    }

    public void runSync() {
        while (memory.get((int)getPC()) != 0) {
            tick();
        }
    }

    public void run() {
        mBackgroundThread = new Thread() {
            public void run() {
                while (memory.get((int)getPC()) != 0) {
                    synchronized(mBackgroundThread) {
                        if (breakPoint) {
                            try {
                                mBackgroundThread.wait();
                            } catch (Exception e) {
                            }
                        }
                        tick();
                        try { Thread.sleep(1000); } catch (Exception e) {}
                    }
                }
            }
        };
        mBackgroundThread.start();
    }

    public void step() {
        breakPoint = true;

        if (!isRunning()) {
            run();
        }

        synchronized (mBackgroundThread) {
            mBackgroundThread.notify();
        }
    }

    public void resume() {
        breakPoint = false;
        synchronized (mBackgroundThread) {
            mBackgroundThread.notify();
        }
    }

    public void stop() {
        synchronized(mBackgroundThread) {
            breakPoint = false;
        }
    }

    public void tick() {
        try {
            if (mCurrentInst == null || mCurrentInst.cost == 0) {
                mCurrentInst = Instruction.getInstruction(this, memory.get((int)mPC));

                mCurrentInst.call();
                mPC += 2;

                if (mListener != null) {
                    mListener.onTick();
                }
            }
            mCurrentInst.cost--;
        } catch (Exception e) {
            
            System.out.println(toString());
            System.out.println(mCurrentInst.toString());
            e.printStackTrace();
            breakPoint = true;
        }
    }

    public SixtyEightKListener getListener() {
        return mListener;
    }
    
    public void setListener(final SixtyEightKListener aListener) {
        mListener = aListener;
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

    public long getSP() {
        if (isSupervisor) {
            return mSSP;
        }
        return mUSP;
    }
    
    public void setSP(final long aSP) {
        if (isSupervisor) {
            mSSP = aSP;
        } else {
            mUSP = aSP;
        }
    }
    

    public long getDx(long x) {
        return mDRegisters[(int) x];
    }

    public void setDx(long x, long val) {
        mDRegisters[(int) x] = val;
    }

    public long getAx(long x) {
        if (x == 7) {
            return getSP();
        }
        return mARegisters[(int) x];
    }

    public void setAx(long x, long val) {
        if (x == 7) {
            setSP(x);
            return;
        }
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

    public void setC(boolean value) {
        if (value) {
            mSR |= C_MASK;
        } else {
            mSR &= ~C_MASK;
        }
    }

    public void setV(boolean value) {
        if (value) {
            mSR |= V_MASK;
        } else {
            mSR &= ~V_MASK;
        }
    }

    public void setZ(boolean value) {
        if (value) {
            mSR |= Z_MASK;
        } else {
            mSR &= ~Z_MASK;
        }
    }

    public void setN(boolean value) {
        if (value) {
            mSR |= N_MASK;
        } else {
            mSR &= ~N_MASK;
        }
    }

    public void setX(boolean value) {
        if (value) {
            mSR |= X_MASK;
        } else {
            mSR &= ~X_MASK;
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

        builder.append("    A[0-6]: (");
        for (int i = 0; i < 7; ++i) {
            builder.append("0x" + Long.toHexString(mARegisters[i]));

            if (i != 7) 
                builder.append(",");
        }
        builder.append(")\n");
        builder.append("    SSP:  0x" + Long.toHexString(mSSP) + "\n");
        builder.append("    USP:  0x" + Long.toHexString(mUSP) + "\n");
        builder.append("++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");

        return builder.toString();
    }

    public static interface SixtyEightKListener {
        public void onTick();
    }
}
