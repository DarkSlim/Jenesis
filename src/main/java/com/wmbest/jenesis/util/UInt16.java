package com.wmbest.jenesis.util;

public class UInt16 {
    private int mValue;

    /**
     * Gets the value as a 32 bit signed int 
     *
     * \return the 16 bit unsigned integer stored in mValue
     */
    public int get() {
        return mValue;
    }

    /**
     * Sets a location in memory to a value
     *
     * \param aVal a signed int value that will be truncated to a 16 bit unsigned value
     */
    public void set(int aValue) {
        mValue = aValue & 0xffff;
    }

    public int incr() {
        set(mValue + 1);
        return mValue;
    }
}
