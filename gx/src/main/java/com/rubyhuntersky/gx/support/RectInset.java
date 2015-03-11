package com.rubyhuntersky.gx.support;

import android.graphics.RectF;

/**
 * @author wehjin
 * @since 1/30/15.
 */

public interface RectInset {

    public RectF applyToRect(RectF original);

    public static class Exact implements RectInset {
        public float left;
        public float right;
        public float top;
        public float bottom;

        public Exact(float left, float top, float right, float bottom) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
        }

        public RectF applyToRect(RectF original) {
            return new RectF(original.left + left, original.top + top, original.right - right,
                  original.bottom - bottom);
        }
    }

    public static class InsideSquare implements RectInset {
        private final float anchorX;
        private final float anchorY;
        private final float scale;

        public InsideSquare(float anchorX, float anchorY, float scale) {

            this.anchorX = anchorX;
            this.anchorY = anchorY;
            this.scale = scale;
        }

        @Override
        public RectF applyToRect(RectF original) {
            float originX = original.left + anchorX * original.width();
            float originY = original.top + anchorY * original.height();

            float size = Math.min(original.width(), original.height());
            float scaledSize = Math.round(scale * size);
            float left = Math.round(originX - scaledSize * anchorX);
            float top = Math.round(originY - scaledSize * anchorY);
            return new RectF(left, top, left + scaledSize, top + scaledSize);
        }
    }


    public static class LeftSquare implements RectInset {
        private final float slideRight;

        public LeftSquare() {
            this(0);
        }

        public LeftSquare(float slideRight) {
            this.slideRight = slideRight;
        }

        @Override
        public RectF applyToRect(RectF original) {
            return new RectF(original.left + slideRight, original.top, original.left + original.height() + slideRight,
                  original.bottom);
        }
    }

    public static class Square implements RectInset {

        private final float centerFractionX;

        public Square(float centerFractionX) {
            this.centerFractionX = centerFractionX;
        }

        @Override
        public RectF applyToRect(RectF original) {
            float centerX = original.left + centerFractionX * original.width();
            float width = original.height();
            float left = Math.round(centerX - width / 2);
            return new RectF(left, original.top, left + width, original.bottom);
        }
    }

    public static class RightSquare implements RectInset {

        @Override
        public RectF applyToRect(RectF original) {
            return new RectF(original.right - original.height(), original.top, original.right, original.bottom);
        }
    }

    public static class NotLeftSquare implements RectInset {
        private final float slideLeft;

        public NotLeftSquare(float slideLeft) {
            this.slideLeft = slideLeft;
        }

        public NotLeftSquare() {
            this(0);
        }

        @Override
        public RectF applyToRect(RectF original) {
            return new RectF(original.height() - slideLeft, original.top, original.right - slideLeft, original.bottom);
        }
    }
}
