package com.wmbest.jenesis.util;

public interface Addressable {
    public int get(int index);
    public void set(int index, int value) throws ModificationException;
    public int size();
    
    public static class ModificationException extends RuntimeException {
    }
}
