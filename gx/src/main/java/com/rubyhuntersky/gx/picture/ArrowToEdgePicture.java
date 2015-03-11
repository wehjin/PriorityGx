package com.rubyhuntersky.gx.picture;

import android.graphics.RectF;

/**
 * @author wehjin
 * @since 2/15/15.
 */

public class ArrowToEdgePicture extends Picture {

    private final float penWidth;
    private final RectF perimeter;
    private final int azimuth;
    private final float sweep;
    private final int color;

    public ArrowToEdgePicture(int color, float sweep, float penWidth, RectF perimeter, int azimuth) {
        this.color = color;
        this.sweep = sweep;
        this.penWidth = penWidth;
        this.perimeter = perimeter;
        this.azimuth = azimuth;
    }

    public int getColor() {
        return color;
    }

    public float getPenWidth() {
        return penWidth;
    }

    public RectF getPerimeter() {
        return perimeter;
    }

    public float getSweep() {
        return sweep;
    }

    public int getAzimuth() {
        return azimuth;
    }
}
