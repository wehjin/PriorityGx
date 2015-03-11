package com.rubyhuntersky.gx.picture;

import android.graphics.RectF;

/**
 * @author wehjin
 * @since 1/29/15.
 */

public class TextLinePicture extends Picture {
    private final String text;
    private final Integer color;
    private final RectF perimeter;

    public TextLinePicture(String text, Integer color, RectF perimeter) {
        super();
        this.text = text;
        this.color = color;
        this.perimeter = perimeter;
    }

    public String getText() {
        return text;
    }

    public Integer getColor() {
        return color;
    }

    public RectF getPerimeter() {
        return perimeter;
    }
}
