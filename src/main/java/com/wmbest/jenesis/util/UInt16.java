package com.wmbest.jenesis.util;

import java.util.ArrayList;

public class UInt16 {
    private int mValue;

    public UInt16() {}

    public UInt16(long aVal) {
        mValue = (int) (aVal & 0xffffL);
    }

    public UInt16(int aVal) {
        mValue = aVal & 0xffff;
    }

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

    /**
     * Increments the current Value and returns it
     * This will ignore overflow setting 0xffff + 1 to 0x0000
     * 
     * \return int the 16bit unsigned integer stored in mValue
     */
    public int incr() {
        set(mValue + 1);
        return mValue;
    }

    /**
     * Splits a long value into an array of UInt16s
     *
     * \param aVal a long value > 0x0
     * \return an array of UInt16 array with max size 4
     */
    public static UInt16[] split(long aVal) {
        ArrayList<UInt16> array = new ArrayList<UInt16>();
        do {
            array.add(new UInt16(aVal));
        } while ((aVal >>= 16) > 0);

        return (UInt16[]) array.toArray(new UInt16[0]);
    }
}
