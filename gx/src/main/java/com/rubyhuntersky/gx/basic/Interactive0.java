package com.rubyhuntersky.gx.basic;

/**
 * @author wehjin
 * @since 2/16/15.
 */

public interface Interactive0 extends Interactive<Void> {

    public Interactive0 inset0(float fraction);
    public Interactive0 inset0(float widthFraction, float heightFraction);

    public Interactive0 overlay0(Interactive0 overlay, int inset);
}
