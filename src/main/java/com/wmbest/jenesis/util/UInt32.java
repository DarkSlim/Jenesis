package com.wmbest.jenesis.util;

public class UInt32 {
    long mValue;

    /**
     * Gets the value as a 64 bit signed long
     *
     * \return the 32 bit unsigned integer stored in mValue
     */
    public long get() {
        return mValue;
    }

    /**
     * Sets a location in memory to a value
     *
     * \param aVal a signed long value that will be truncated to a 32 bit unsigned value
     */
    public void set(long aValue) {
        mValue = aValue & 0xffffffff;
    }

    public long incr() {
        set(mValue + 1);
        return mValue;
    }
}
