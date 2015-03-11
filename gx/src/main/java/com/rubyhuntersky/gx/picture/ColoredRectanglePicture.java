package com.rubyhuntersky.gx.picture;

import android.graphics.RectF;

/**
 * @author wehjin
 * @since 1/28/15.
 */

public class ColoredRectanglePicture extends Picture {
    private final RectF rect;
    private final Integer color;

    public ColoredRectanglePicture(Integer color, RectF rect) {
        this.rect = rect;
        this.color = color;
    }

    public RectF getRect() {
        return rect;
    }

    public int getColor() {
        return color;
    }
}
