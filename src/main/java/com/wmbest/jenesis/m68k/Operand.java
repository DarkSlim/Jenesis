package com.wmbest.jenesis.m68k;

import com.wmbest.jenesis.m68k.instructions.Instruction;
import com.wmbest.jenesis.m68k.instructions.Instruction.UnsupportedOpcodeException;

public class Operand {
    public int mode;
    public int reg;
    public int size = Instruction.WORD;
    public SixtyEightK cpu;

    // Bytes for direct and indexed modes... 
    // get this value in the pre, do not incr in the get or set
    public int upperWord;
    public int lowerWord;

    public boolean disable;

    public void preHandle() {
        if (!disable) {
            switch(mode) {
                case 4:
                    if (reg == 7) {
                        size = 0x3;    
                    }
                    cpu.setAx(reg, cpu.getAx(reg) - Instruction.sizeToByte(size));
                    break;
                case 5:
                case 6: //TODO There are other modes for other 680x0s but im ignoring them.
                    lowerWord = immediateWord();
                    break;
                case 7:
                    lowerWord = immediateWord();
                    if (size == Instruction.LONG || reg == 1) {
                        upperWord = lowerWord;
                        lowerWord = immediateWord();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void postHandle() {
        if (!disable) {
            if (mode == 3) {
                if (reg == 7) {
                    size = 0x3;    
                }
                cpu.setAx(reg, cpu.getAx(reg) + Instruction.sizeToByte(size));
            }
        }
    }

    public String toString() {
        switch(mode) {
            case 0:
                return "D" + reg;
            case 1:
                return "A" + reg;
            case 2:
                return "(A" + reg + ")";
            case 3:
                return "(A" + reg + ")+";
            case 4:
                return "-(A" + reg + ")";
            case 5:
                return "(0x" + Long.toHexString(lowerWord) + ",A" + reg + ")";
            case 6:
                return "MODE SIX";
            case 7:
                switch(reg) {
                    case 0:
                        return "(0x" + Long.toHexString(lowerWord) + ")";
                    case 1:
                        long val = ((long)upperWord << 16) + lowerWord;
                        return "(0x" + Long.toHexString(val) + ")";
                    case 4:
                        if (size == Instruction.LONG) {
                            return "$" + ((long)upperWord << 16) + lowerWord + ".L";
                        } else {
                            return "$" + lowerWord;
                        }
                    default:
                        return "CASES 1 or 2";

                }
            default:
                return "INVALID";
        }
    }

    public long getVal() {
        switch(mode) {
            case 0:
                return cpu.getDx(reg);
            case 1:
                return cpu.getAx(reg);
            case 2:
                return getIndirectAx(reg);
            case 3:
                return getIndirectAx(reg);
            case 4:
                return getIndirectAx(reg);
            case 5:
                return getIndirectAxWithOffset(reg, lowerWord);
            case 6:
                return getModeSix();
            case 7:
                return getModeSeven();
            default:
                return -1;
        }
    }

    private int getIndexedOffset() {
        int offset = lowerWord & 0xff;
        int xn = (lowerWord & 0xf000) >> 12;

        boolean isLong = (lowerWord & 0x0800) >> 11 == 1;

        int scale = (isLong) ? 2 : 1;

        if (xn >= 8) {
            offset += cpu.getAx(xn - 8) * scale;
        } else {
            offset += cpu.getDx(xn) * scale;
        }

        return offset;
    }

    private long getModeSix() {
        int offset = getIndexedOffset();
        System.out.println("OFFSET: " + offset);
        return getIndirectAxWithOffset(reg, offset);
    }

    private void setModeSix(long val) {
        int offset = getIndexedOffset();
        setIndirectAxWithOffset(reg, offset, val);
    }

    private long getModeSeven() {
        switch(reg) {
            case 0:
                return getIndirect(lowerWord);
            case 1:
                long val = ((long)upperWord << 16) + lowerWord;
                return getIndirect((int) val);
            case 4:
                if (size == Instruction.LONG) {
                    return ((long)upperWord << 16) + lowerWord;
                } else {
                    return lowerWord;
                }
            case 2:
                return getIndirectPCWithOffset(lowerWord);
            case 3:
                return getIndirectPCWithOffset(getIndexedOffset());
            default:
                return -1;
        }
    }

    private void setModeSeven(long val) {
        switch(reg) {
            case 0:
                setIndirect(lowerWord, val & 0xffffL);
                return;
            case 1:
                long index = ((long)upperWord << 16) + lowerWord;
                setIndirectLong((int) index, val);
                return;
            case 2:
                setIndirectPCWithOffset(lowerWord, val);
                return;
            case 3:
                setIndirectPCWithOffset(getIndexedOffset(), val);
                return;

        }
        throw new UnsupportedOperandException("Invalid Operand");
    }

    public void setVal(long val) {
        switch(mode) {
            case 0:
                cpu.setDx(reg, val);
                break;
            case 1:
                cpu.setAx(reg, val);
                break;
            case 2: //!< Indirect PreIncrement... see {@link #preHandle()}
            case 3: //!< Indirect PostIncrement... see {@link #postHandle()}
            case 4:
                setIndirectAx(reg, val); // Indirect Pre Increment 
                break;
            case 5:
                setIndirectAxWithOffset(reg, lowerWord, val);
                break;
            case 6:
                setModeSix(val);
                break;
            case 7:
                setModeSeven(val);
                break;
        }
    }

    public int immediateByte() {
        int offset = (int) (cpu.incrPC() & 0xffff);
        return cpu.memory.get(offset) & 0xff;
    }

    public int immediateWord() {
        int offset = (int) (cpu.incrPC() & 0xffff);
        return cpu.memory.get(offset) & 0xffff;
    }

    public long immediateLong() {
        return ((((long)immediateWord()) << 16) + immediateWord()) & 0xffffffff;
    }

    public int getIndirectAx(int x) {
        return getIndirect((int) cpu.getAx(x));
    }

    public void setIndirectAx(int x, long val) {
        setIndirect((int) cpu.getAx(x), val);
    }

    public int getIndirect(int mem) {
        return cpu.memory.get(mem);
    }

    public long getIndirectLong(int mem) {
        return cpu.memory.getLong(mem);
    }

    public void setIndirect(int mem, long val) {
        cpu.memory.set(mem, (int) val);
    }

    public void setIndirectLong(int mem, long val) {
        cpu.memory.setLong(mem, val);
    }

    public int getIndirectAxWithOffset(int x, int offset) {
        return getIndirect((int) cpu.getAx(x) + offset);
    }

    public void setIndirectAxWithOffset(int x, int offset, long val) {
        setIndirect((int) cpu.getAx(x) + offset, val);
    }

    public int getIndirectPCWithOffset(int offset) {
        return getIndirect((int) cpu.getPC() + offset);
    }

    public void setIndirectPCWithOffset(int offset, long val) {
        setIndirect((int) cpu.getPC() + offset, val);
    }

    public static class UnsupportedOperandException extends RuntimeException {
        
        public UnsupportedOperandException(String msg) {
            super(msg);
        }
    }
}
