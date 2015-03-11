package com.rubyhuntersky.gx.interaction;

/**
 * @author wehjin
 * @since 1/28/15.
 */

public interface TouchInteraction {
    void move(float x, float y);
    void cancel();
    void up();
}
