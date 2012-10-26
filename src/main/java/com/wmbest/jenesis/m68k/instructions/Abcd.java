package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;

public class Abcd extends MulAndInstruction {

    int mode;
    int regX;
    int regY;
    String ops;

    @Override
    public void setup(int value) {
        name = "ABCD";
        mode = (value >> 3) & 0x1;
        regX = value & 0x7;
        regY = (value >> 8) & 0x7;

        // Since we disabled the operands, we must predecrement the registers before we begin
        if (mode == 1) {
            cpu.setAx(regX, cpu.getAx(regX) - 2);
            cpu.setAx(regY, cpu.getAx(regY) - 2);
            ops = "-(A" + regY + "), -(A" + regX + ")";
        } else {
            ops = "D" + regY + ", D" + regX + "";
        }
    }

    @Override
    public void handle() {
        long src, dst;
        if (mode == 1) {
            src = operands[0].getIndirectAx(regY);
            dst = operands[0].getIndirectAx(regX);
        } else {
            src = cpu.getDx(regY);
            dst = cpu.getDx(regX);
        }

        src = getUnpackedDecimal(src);
        dst = getUnpackedDecimal(dst);

        src = src + dst + (cpu.X() ? 1 : 0);
        cpu.setX(false);

        if (src > 99) {
            src = 100 - src;
        }

        if (mode == 1) {
            operands[0].setIndirectAx(regY, src);
        } else {
            cpu.setDx(regY, src);
        }


    }

    public long getUnpackedDecimal(long value) {
        long num1 = value & 0xf;
        long num2 = (value >> 4) & 0xf;

        return num2 * 10 + num1;
    }

    public long getPackedBinary(long value) {

        if (value < 0) {
            value = value + 100;
        }

        long num2 = (long) (value / 10);
        long num1 = (long) (value % 10);

        return ((num2 << 4) & num1) & 0xff;
    }

    @Override
    public String disassemble() {
        return "ABCD\t" + ops;
    }

    @Override
    public void preHandle() {
        super.preHandle();

        operands[0].disable = true;
    }
}
