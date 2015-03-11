package com.rubyhuntersky.gx.support;

/**
 * @author wehjin
 * @since 2/14/15.
 */

abstract public class ToVoidAdapter<T> implements Adapter<T, Void> {
    @Override
    public final Void getResult(T innerResult) {
        return null;
    }
}
