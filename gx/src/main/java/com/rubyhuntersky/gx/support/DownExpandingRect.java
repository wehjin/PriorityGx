package com.rubyhuntersky.gx.support;

import android.graphics.RectF;

/**
 * @author wehjin
 * @since 2/7/15.
 */

public class DownExpandingRect {

    private final RectF baseRect;
    private final RectF finalRect;

    public DownExpandingRect(RectF rect) {
        this(rect.left, rect.top, rect.right, rect.bottom);
    }

    public DownExpandingRect(float left, float top, float right, float bottom) {
        baseRect = new RectF(left, top, right, bottom);
        finalRect = new RectF(baseRect);
    }

    public RectF getRect() {
        return finalRect;
    }

    public void setExpandDown(float expandDown) {
        finalRect.bottom = baseRect.bottom + expandDown;
    }
}
