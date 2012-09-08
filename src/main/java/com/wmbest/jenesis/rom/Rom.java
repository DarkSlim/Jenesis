package com.wmbest.jenesis.rom;

import com.wmbest.jenesis.util.Addressable;

public abstract class Rom implements Addressable {

    private int[] mRomBank = new int[0x1000000];

    public abstract Rom fromFile();

    public int get(int index) {
        return mRomBank[index];
    }

    public void set(int index, int value) {
        throw new Addressable.ModificationException();
    }

    public int size() {
        return mRomBank.length;
    }
}
