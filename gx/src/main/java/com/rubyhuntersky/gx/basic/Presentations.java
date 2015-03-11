package com.rubyhuntersky.gx.basic;

/**
 * @author wehjin
 * @since 2/16/15.
 */

public class Presentations {

    public static Presentation getClearPicture(final Console console) {
        return new Presentation() {

            boolean isDismissed;

            @Override
            public boolean isDismissed() {
                return isDismissed;
            }

            @Override
            public void dismiss() {
                if (isDismissed) {
                    return;
                }
                console.onPicture(null);
                isDismissed = true;
            }
        };
    }
}
