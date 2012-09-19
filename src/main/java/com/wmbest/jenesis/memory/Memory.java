package com.wmbest.jenesis.memory;

import java.nio.ByteBuffer;
import java.io.*;

public class Memory { 
    
    public static ByteBuffer buffer = ByteBuffer.allocate(0xffffff);
    private MemoryChangeListener mListener;

    public MemoryChangeListener getListener() {
        return mListener;
    }
    
    public void setListener(final MemoryChangeListener aListener) {
        mListener = aListener;
    }

    public void loadFromFile(final String path) throws FileNotFoundException {
        FileInputStream is = new FileInputStream(path);
        DataInputStream data = new DataInputStream(is);

        buffer.clear();
        
        try {
            while (true) {
                put(data.readChar());
            }
        } catch (Exception e) {
            // DONE READING
        }
    }

    public synchronized int get(int index) {
        return getWord(index);
    }

    public synchronized void set(int index, int val) {
        setWord(index, val);
    }

    public synchronized int getByte(int index) {
        return (int) buffer.get(index);
    }

    public synchronized void setByte(int index, int val) {
        System.out.println("MEMORY WRITE.B[0x" + Integer.toHexString(index) + "]: 0x" + Integer.toHexString(val));
        buffer.put(index, (byte)val);

        if (mListener != null) {
            mListener.onMemoryChanged(index, 1, val);
        }
    }

    public synchronized int getWord(int index) {
        return (int) buffer.getChar(index);
    }

    public synchronized void setWord(int index, int val) {
        System.out.println("MEMORY WRITE.W[0x" + Integer.toHexString(index) + "]: 0x" + Integer.toHexString(val));
        buffer.putChar(index, (char)val);

        if (mListener != null) {
            mListener.onMemoryChanged(index, 2, val);
        }
    }

    public synchronized int getLong(int index) {
        return (int) buffer.getChar(index);
    }

    public synchronized void setLong(int index, long val) {
        System.out.println("MEMORY WRITE.L[0x" + Integer.toHexString(index) + "]: 0x" + Long.toHexString(val));
        buffer.putInt(index, (int)val);

        if (mListener != null) {
            mListener.onMemoryChanged(index, 4, val);
        }
    }

    public synchronized void put(int val) {
        buffer.putChar((char)val);

        if (mListener != null) {
            mListener.onMemoryChanged(buffer.position(), 2, val);
        }
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

    public static interface MemoryChangeListener {
        public void onMemoryChanged(int index, int size, long val);
    }
}
