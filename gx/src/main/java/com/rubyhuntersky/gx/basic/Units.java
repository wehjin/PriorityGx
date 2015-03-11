package com.rubyhuntersky.gx.basic;

import android.graphics.RectF;

import com.rubyhuntersky.gx.units.ZeroUnits;

/**
 * @author wehjin
 * @since 2/11/15.
 */

public interface Units {

    RectF getPerimeter();

    Units withPerimeter(RectF perimeter);

    float getStrokeWidth();

    float getTextHeight();

    float getTapWidth();

    public static Units ZERO = new ZeroUnits();
    public static RectF ZERO_RECT = new RectF();
}
