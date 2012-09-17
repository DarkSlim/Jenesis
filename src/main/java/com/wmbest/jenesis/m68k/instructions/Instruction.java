package com.wmbest.jenesis.m68k.instructions;

import com.wmbest.jenesis.m68k.*;
import com.wmbest.jenesis.util.*;

public abstract class Instruction {

    public SixtyEightK cpu;
    public int value;
    public int cost = 1;

    int size;

    public Operand[] operands = new Operand[2];

    public String name = "INVALID";
    
    public static final int BYTE = 1;
    public static final int WORD = 3;
    public static final int LONG = 2;
    public static final int B_SCALE = 1;
    public static final int W_SCALE = 2;
    public static final int L_SCALE = 4;

    public static final String[] SIZE_STRING = new String[] {"INVALID", "BYTE", "LONG", "WORD"};
    
    protected abstract void handle();
    public String disassemble() {
        return name;
    }

    public void call() {
        preHandle();

        handle();

        postHandle();
    }

    protected static boolean checkBits(int value, int mask) {
        return (value & mask) == mask;
    }

    private void preHandle() {
        for (int i = 0; i < operands.length; i++) {
            if (operands[i] != null)
                operands[i].preHandle();
        }
    }

    private void postHandle() {
        for (int i = 0; i < operands.length; i++) {
            if (operands[i] != null)
                operands[i].postHandle();
        }
    }

    public void setup(int value) {
        this.value = value;
    }

    public static int sizeToByte(int size) {
        switch(size) {
            case BYTE:
                return B_SCALE;
            case LONG:
                return L_SCALE;
            case WORD:
                return W_SCALE;
            default:
                throw new UnsupportedSizeException(size);
        }
    }

    public static long signExtend(int val, int size) {
        if (size == WORD) {
            val = val & 0x0000ffff;
            if ((0x8000 & val) == 0x8000) {
                return 0xffff0000 | val;
            }
        } else if (size == BYTE) {
            val = val & 0x000000ff;
            if ((0x80 & val) == 0x80) {
                return 0xffffff00 | val;
            }
        }

        return val;
    }

    public static Instruction getInstruction(SixtyEightK cpu, int value) {
        int opcode = (value & 0xf000) >> 12;
        Instruction result = null;

        switch(opcode) {
            case 0:
                result = ImmediateInstruction.getInstruction(value);
                if (result == null) {
                    result = BitwiseInstruction.getInstruction(value);
                }
                break;
            // MOVE COMMANDS
            case 1:
            case 2:
            case 3:
                result = new Move();
                break;
            case 4:
                result = SystemInstruction.getInstruction(value);
                break;
            case 5:
            case 6:
            case 7:
                result = QuickAndBranchInstruction.getInstruction(value);
                break;
            case 13:
                result = new Add();
                break;
            default:
                throw new UnsupportedOpcodeException(value);
        }

        result.cpu = cpu;
        result.setup(value);

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getClass().getName() + "\n");
        builder.append("---------------Instruction--------------\n");
        builder.append("Binary Value: ");
        builder.append(getBinary(value));
        builder.append("\n");
        builder.append("Hex Value: ");
        builder.append(getHex(value));
        builder.append("\n");
        builder.append("----------------------------------------\n");
        builder.append("******************************************************");
        return builder.toString();
    }

    public static class UnsupportedSizeException extends UnsupportedOpcodeException {
        public UnsupportedSizeException(long value) {
            super(value);
            title = "\n***************** UNSUPPORTED SIZE *******************\n";
        }
    }

    public static class UnsupportedOpcodeException extends RuntimeException {
        long value;
        String title = "\n**************** UNSUPPORTED OPCODE ******************\n";

        public UnsupportedOpcodeException(long value) {
            this.value = value;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(title);
            builder.append("---------------Instruction--------------\n");
            builder.append("Binary Value: ");
            builder.append(getBinary(value));
            builder.append("\n");
            builder.append("Hex Value: ");
            builder.append(getHex(value));
            builder.append("\n");
            builder.append("----------------------------------------\n");
            builder.append("******************************************************\n");
            return builder.toString();
        }

    }
    private static String getHex(long value) {
        String hex = Long.toHexString(value);
        while (hex.length() < 2) {
            hex = "0" + hex;
        }

        StringBuilder result = new StringBuilder();
        String[] values = hex.split("(?<=\\G..)");
        for (String val : values) {
            result.append(val);
            result.append(" ");
        }

        return result.toString();
    }


    private static String getBinary(long value) {
        String binary = Long.toBinaryString(value);
        while (binary.length() < 16) {
            binary = "0" + binary;
        }

        StringBuilder result = new StringBuilder();
        String[] values = binary.split("(?<=\\G....)");
        for (String val : values) {
            result.append(val);
            result.append(" ");
        }

        return result.toString();
    }
}
