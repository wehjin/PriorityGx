package com.rubyhuntersky.gx.basic;

/**
 * @author wehjin
 * @since 1/27/15.
 */

public interface Presentation {

    boolean isDismissed();

    void dismiss();

    public static Presentation EMPTY = new Presentation() {
        @Override
        public boolean isDismissed() {
            return true;
        }

        @Override
        public void dismiss() {
        }
    };
}
