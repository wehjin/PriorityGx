package com.rubyhuntersky.gx.support;

/**
 * @author wehjin
 * @since 2/14/15.
 */
public interface Adapter<Inner, Outer> {
    Inner getSettings(Outer outerSettings);
    Outer getResult(Inner innerResult);
}
