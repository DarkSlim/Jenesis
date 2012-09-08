package com.wmbest.jenesis.m68k;

import com.wmbest.jenesis.m68k.instructions.Instruction;
import com.wmbest.jenesis.m68k.instructions.Instruction.UnsupportedOpcodeException;

public class Operand {
    public int mode;
    public int reg;
    public int size;
    public SixtyEightK cpu;

    public void preHandle() {

        if (mode == 4) {
            if (reg == 7) {
                size = 0x3;    
            }
            cpu.setAx(reg, cpu.getAx(reg) - Instruction.sizeToByte(size));
        }
    }

    public void postHandle() {
        if (mode == 3) {
            if (reg == 7) {
                size = 0x3;    
            }
            cpu.setAx(reg, cpu.getAx(reg) + Instruction.sizeToByte(size));
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
                int offset = (int) (cpu.incrPC() & 0xffff);
                return getIndirectAxWithOffset(reg, offset);
            case 7:
                return getModeSeven();
            default:
                return -1;
        }
    }

    public long getModeSeven() {
        switch(reg) {
            case 4:
                return immediateByte();
            default:
                return -1;
        }
    }

    public void setVal(long val) {
        switch(mode) {
            case 0:
                cpu.setDx(reg, val);
                break;
            case 1:
                cpu.setAx(reg, val);
                break;
            case 2:
                setIndirectAx(reg, val);
                break;
            case 3:
                setIndirectAx(reg, val); // Indirect Post Increment (happens in the post call)
                break;
            case 4:
                setIndirectAx(reg, val); // Indirect Pre Increment 
                break;
            case 5:
                int offset = (int) (cpu.incrPC() & 0xffff);
                break;
        }
    }

    public int immediateByte() {
        int offset = (int) (cpu.incrPC() & 0xffff);
        return cpu.memory[offset & 0xff];
    }

    public int immediateWord() {
        int offset = (int) (cpu.incrPC() & 0xffff);
        return cpu.memory[offset & 0xffff];
    }

    public long immediateLong() {
        return ((immediateWord() << 16) + immediateWord()) & 0xffffffff;
    }

    public int getIndirectAx(int x) {
        return cpu.memory[(int) cpu.getAx(x)];
    }

    public void setIndirectAx(int x, long val) {
        cpu.memory[(int) cpu.getAx(x)] = (int) val;
    }

    public int getIndirectAxWithOffset(int x, int offset) {
        return cpu.memory[(int) cpu.getAx(x) + offset];
    }

    public void setIndirectAxWithOffset(int x, int offset, long val) {
        cpu.memory[(int) cpu.getAx(x) + offset] = (int) val;
    }
}
