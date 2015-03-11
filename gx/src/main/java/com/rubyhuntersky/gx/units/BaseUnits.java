package com.rubyhuntersky.gx.units;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Units;

/**
 * @author wehjin
 * @since 3/6/15.
 */

public class BaseUnits implements Units {

    private final RectF perimeter;
    private final float penWidth;
    private final float textHeight;
    private final float fingerWidth;

    public BaseUnits(RectF perimeter, float penWidth, float textHeight, float fingerWidth) {
        this.perimeter = perimeter;
        this.penWidth = penWidth;
        this.textHeight = textHeight;
        this.fingerWidth = fingerWidth;
    }

    @Override
    public RectF getPerimeter() {

        return perimeter;
    }

    @Override
    public float getStrokeWidth() {
        return penWidth;
    }

    @Override
    public float getTextHeight() {
        return textHeight;
    }

    @Override
    public float getTapWidth() {
        return fingerWidth;
    }

    public BaseUnits withPerimeter(RectF perimeter) {
        return new BaseUnits(perimeter, penWidth, textHeight, fingerWidth);
    }

    public BaseUnits withStrokeWidth(float penWidth) {
        return new BaseUnits(perimeter, penWidth, textHeight, fingerWidth);
    }

    public BaseUnits withTextHeight(float textHeight) {
        return new BaseUnits(perimeter, penWidth, textHeight, fingerWidth);
    }

    public BaseUnits withTapWidth(float fingerWidth) {
        return new BaseUnits(perimeter, penWidth, textHeight, fingerWidth);
    }
}
