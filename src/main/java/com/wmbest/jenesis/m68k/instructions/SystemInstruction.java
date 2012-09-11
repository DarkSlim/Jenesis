package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class SystemInstruction extends Instruction {
    
    public static SystemInstruction getInstruction(int value) {
        // 16 bit constant value functions
        switch(value) {
            case 0x4AFC:
                // TODO: ILLEGAL
            case 0x4E70:
                // TODO: RESET
            case 0x4E71:
                // TODO: NOP
            case 0x4E72:
                // TODO: STOP
            case 0x4E73:
                // TODO: RTE
            case 0x4E75:
                // TODO: RTS 
            case 0x4E76:
                // TODO: TRAPV 
            case 0x4E77:
                // TODO: RTR
        }

        int secondAndThird = (value & 0x0ff0) >> 4;
        // 12 bit constant functions
        switch (secondAndThird) {
            case 0xE4:
                // TODO TRAP
            case 0xE5:
                // TODO LINK (ea MODE 0b010)
                // TODO UNLK (ea MODE 0b011)
            case 0xE6:
                // TODO MOVE USP
        }

        // 8 bit constant functions
        int secondByte = (value & 0x0f00) >> 8;

        switch(secondByte) {
            case 0x0:
                // TODO MOVE from SR (size 0b11)
                // TODO NEGX (size 0b00 ~ 0b10)
            case 0x4:
                // TODO MOVE to CCR (size 0b11)
                // TODO NEG (size 0b00 ~ 0b10)
            case 0x6:
                // TODO MOVE to SR (size 0b11)
                // TODO NOT (size 0b00 ~ 0b10)
            case 0x8:
                // TODO EXT (size 0b10 ~ 0b11, ea MODE 0b000)
                // TODO MOVEM (size 0b10 ~ 0b11)
                // TODO NCBD (size 0b00)
                // TODO SWAP (size 0b01, ea MODE 0b000)
                // TODO PEA (size 0b01)
            case 0xa:
                // ILLEGAL ALREADY TAKEN CARE OF
                //TODO TAS (size 0b11)
                //TODO TST (size 0b00 ~ 0b10)
            case 0xc:
                // TODO MOVEM (size 0b10 ~ 0b11)
            case 0xe:
                // RESET, NOP, STOP, RTE, RTS, TRAPV, RTR Already Handled
                //TODO JSR (size 0b10)
                //TODO JMP (size 0b11)
        }

        throw new UnsupportedOpcodeException(value);
    }
}
