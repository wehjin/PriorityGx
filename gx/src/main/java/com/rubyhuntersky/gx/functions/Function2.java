package com.rubyhuntersky.gx.functions;

/**
 * @author wehjin
 * @since 1/29/15.
 */

public interface Function2<T1, T2, Result> {
    Result call(T1 t1, T2 t2);
}
