package com.rubyhuntersky.gx.picture;

import android.graphics.RectF;

/**
 * @author wehjin
 * @since 1/30/15.
 */

public class TextMultiLinePicture extends Picture {
    private final String text;
    private final Integer color;
    private final Integer maxLineCount;
    private final RectF perimeter;

    public TextMultiLinePicture(String text, Integer color, Integer maxLineCount, RectF perimeter) {
        super();
        this.text = text;
        this.color = color;
        this.maxLineCount = maxLineCount;
        this.perimeter = perimeter;
    }

    public String getText() {
        return text;
    }

    public Integer getColor() {
        return color;
    }

    public Integer getMaxLineCount() {
        return maxLineCount;
    }

    public RectF getPerimeter() {
        return perimeter;
    }
}
