package com.wmbest.jenesis.memory;

import java.nio.ByteBuffer;

public class Memory { 
    
    public ByteBuffer buffer = ByteBuffer.allocate(0xffffff);

    public int get(int index) {
        return (int) buffer.getChar(index);
    }

    public void set(int index, int val) {
        buffer.putChar(index, (char)val);
    }

    public void put(int val) {
        buffer.putChar((char)val);
    }
}
