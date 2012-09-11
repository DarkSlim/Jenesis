package com.wmbest.jenesis.memory;

import java.nio.ByteBuffer;

public class Memory { 
    
    public static ByteBuffer buffer = ByteBuffer.allocate(0xffffff);

    public int get(int index) {
        return (int) buffer.getChar(index);
    }

    public void set(int index, int val) {
        System.out.println("MEMORY WRITE[0x" + Integer.toHexString(index) + "]: 0x" + Integer.toHexString(val));
        buffer.putChar(index, (char)val);
    }

    public void put(int val) {
        buffer.putChar((char)val);
    }

    public void clear() {
        buffer.clear(); 

        try {
            while(true)
                put(0x0);
        } catch (Exception e) {
            // Run until we exceed the buffer length then reset again
        }

        buffer.clear(); 
    }
}
