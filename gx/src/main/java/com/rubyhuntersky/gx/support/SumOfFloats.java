package com.rubyhuntersky.gx.support;

/**
 * @author wehjin
 * @since 2/1/15.
 */

public final class SumOfFloats {

    private final boolean shouldRoundValues;
    private float baseValue;
    private float extraValue = 0f;
    private ChangeListener changeListener;

    public SumOfFloats(float baseValue, boolean shouldRoundValues, ChangeListener changeListener) {
        this.shouldRoundValues = shouldRoundValues;
        this.baseValue = shouldRoundValues ? Math.round(baseValue) : baseValue;
        this.changeListener = changeListener;
    }

    public float getValue() {
        return baseValue + extraValue;
    }

    public float getBase() {
        return baseValue;
    }

    public float getExtra() {
        return extraValue;
    }

    public void setExtra(float extraValue) {
        float oldValue = getValue();
        this.extraValue = shouldRoundValues ? Math.round(extraValue) : extraValue;
        if (changeListener != null) {
            float newValue = getValue();
            if (newValue != oldValue) {
                changeListener.onChange();
            }
        }
    }

    public void markExtra() {
        float oldExtra = extraValue;
        baseValue += extraValue;
        extraValue = 0;
        if (changeListener != null) {
            if (oldExtra != 0) {
                changeListener.onChange();
            }
        }
    }

    public void setAndMarkExtra(float extraValue) {
        float oldValue = getValue();
        float oldExtra = this.extraValue;
        this.baseValue += shouldRoundValues ? Math.round(extraValue) : extraValue;
        this.extraValue = 0;
        if (changeListener != null) {
            float newValue = getValue();
            if (newValue != oldValue || extraValue != oldExtra) {
                changeListener.onChange();
            }
        }
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public interface ChangeListener {
        void onChange();
    }
}
