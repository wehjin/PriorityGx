package com.rubyhuntersky.gx.picture;

import android.graphics.RectF;

/**
 * @author wehjin
 * @since 1/28/15.
 */

public class ColoredOvalPicture extends Picture {
    private final RectF rect;
    private final Integer color;

    public ColoredOvalPicture(Integer color, RectF rect) {
        this.rect = rect;
        this.color = color;
    }

    public RectF getRect() {
        return rect;
    }

    public Integer getColor() {
        return color;
    }
}
