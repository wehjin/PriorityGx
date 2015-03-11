package com.rubyhuntersky.gx.support;

import android.graphics.RectF;
import android.support.annotation.NonNull;

/**
 * @author wehjin
 * @since 2/7/15.
 */

public class DownMovingRect {
    private final RectF baseRect = new RectF();
    private final RectF finalRect = new RectF();

    public DownMovingRect(@NonNull RectF rect) {
        this(rect.left, rect.top, rect.right, rect.bottom);
    }

    public DownMovingRect(float left, float top, float right, float bottom) {
        baseRect.set(left, top, right, bottom);
        finalRect.set(baseRect);
    }

    public RectF getBase() {
        return baseRect;
    }

    public RectF getRect() {
        return finalRect;
    }

    public void setMoveDown(float moveDown) {
        finalRect.top = baseRect.top + moveDown;
        finalRect.bottom = baseRect.bottom + moveDown;
    }
}
