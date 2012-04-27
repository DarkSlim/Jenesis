package com.wmbest.jenesis.util;

import java.util.ArrayList;

public class UInt32 {
    long mValue;

    public UInt32() {}

    public UInt32(long aVal) {
        mValue = aVal & 0xffffffff;
    }

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

    /**
     * Splits a long value into an array of UInt32s
     *
     * \param aVal a long value > 0x0
     * \return an array of UInt32 array with max size 2
     */
    public static UInt32[] split(long aVal) {
        ArrayList<UInt32> array = new ArrayList<UInt32>();
        do {
            array.add(new UInt32(aVal));
        } while ((aVal >>= 32) > 0);

        return (UInt32[]) array.toArray(new UInt32[0]);
    }
}
