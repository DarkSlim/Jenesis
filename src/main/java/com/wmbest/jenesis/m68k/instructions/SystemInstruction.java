package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class SystemInstruction extends SEAInstruction {
    
    public static SystemInstruction getInstruction(int value) {
        // 16 bit constant value functions
        switch(value) {
            case 0x4AFC:
                return new Illegal();
            case 0x4E70:
                return new Reset(); 
            case 0x4E71:
                return new Nop();
            case 0x4E72:
                return new Stop();
            case 0x4E73:
                return new Rte();
            case 0x4E75:
                return new Rts();
            case 0x4E76:
                return new Trapv();
            case 0x4E77:
                return new Rtr();
        }

        int secondAndThird = (value & 0x0ff0) >> 4;
        // 12 bit constant functions
        switch (secondAndThird) {
            case 0xE4:
                return new Trap(); 
            case 0xE5:
                /** \todo LINK (ea MODE 0b010) */
                /** \todo UNLK (ea MODE 0b011) */
            case 0xE6:
                /** \todo MOVE USP */
        }

        // 8 bit constant functions
        int secondByte = (value & 0x0f00) >> 8;

        switch(secondByte) {
            case 0x0:
                /** \todo MOVE from SR (size 0b11) */
                /** \todo NEGX (size 0b00 ~ 0b10) */
            case 0x4:
                /** \todo MOVE to CCR (size 0b11) */
                /** \todo NEG (size 0b00 ~ 0b10) */
            case 0x6:
                /** \todo MOVE to SR (size 0b11) */
                /** \todo NOT (size 0b00 ~ 0b10) */
            case 0x8:
                /** \todo EXT (size 0b10 ~ 0b11, ea MODE 0b000) */
                /** \todo MOVEM (size 0b10 ~ 0b11) */
                /** \todo NCBD (size 0b00) */
                /** \todo SWAP (size 0b01, ea MODE 0b000) */
                /** \todo PEA (size 0b01) */
            case 0xa:
                return new Tst();
            case 0xc:
                return new MoveM();
            case 0xe:
                // RESET, NOP, STOP, RTE, RTS, TRAPV, RTR Already Handled
                // TRAP, LINK, UNLK, MOVE USP  Also taken care of already
                /** \todo JSR (size 0b10) */
                /** \todo JMP (size 0b11) */
        }

        int op = (value >> 6) & 0x7;
        if (op == 0x7) {
            return new Lea();
        } else if (op == 0x6) {
            return new Chk();
        }

        throw new UnsupportedOpcodeException(value);
    }

    @Override
    public void setup(int value) {
        super.setup(value);
        operands[0].disable = true;
    }
}
