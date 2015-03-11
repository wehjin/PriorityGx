package com.rubyhuntersky.gx.picture;

import android.graphics.RectF;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class CrossPicture extends Picture {
    private final float penWidth;
    private final RectF perimeter;
    private final int color;

    public CrossPicture(float penWidth, RectF perimeter, int color) {
        super();

        this.penWidth = penWidth;
        this.perimeter = perimeter;
        this.color = color;
    }

    public float getPenWidth() {
        return penWidth;
    }

    public RectF getPerimeter() {
        return perimeter;
    }

    public int getColor() {
        return color;
    }
}
