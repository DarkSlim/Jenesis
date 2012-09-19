package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class SystemInstruction extends SEAInstruction {
    
    public static SystemInstruction getInstruction(int value) {
        // 16 bit constant value functions
        switch(value) {
            case 0x4AFC:
                /** \todo ILLEGAL */
            case 0x4E70:
                /** \todo RESET */
            case 0x4E71:
                return new Nop();
            case 0x4E72:
                /** \todo STOP */
            case 0x4E73:
                /** \todo RTE */
            case 0x4E75:
                /** \todo RTS  */
            case 0x4E76:
                /** \todo TRAPV  */
            case 0x4E77:
                /** \todo RTR */
        }

        int secondAndThird = (value & 0x0ff0) >> 4;
        // 12 bit constant functions
        switch (secondAndThird) {
            case 0xE4:
                /** \todo TRAP */
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
            /** \todo CHK */
        }

        throw new UnsupportedOpcodeException(value);
    }
}
