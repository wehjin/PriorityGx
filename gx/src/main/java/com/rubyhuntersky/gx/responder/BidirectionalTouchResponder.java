package com.rubyhuntersky.gx.responder;

import android.support.annotation.NonNull;

import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.interaction.TouchInteraction;

/**
 * @author wehjin
 * @since 2/1/15.
 */

public class BidirectionalTouchResponder extends TouchOnlyResponder implements Responder {

    private static final int THRESHOLD = 25;
    public static final float DIRECTIONAL_MULTIPLIER = 2f;
    private final Adapter adapter;

    public BidirectionalTouchResponder(@NonNull Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public TouchInteraction getTouchInteraction(final float downX, final float downY) {

        return new TouchInteraction() {

            Gesture gesture = Gesture.UNKNOWN;
            TouchInteraction gestureInteraction;

            @Override
            public void move(float x, float y) {
                if (gesture != Gesture.UNKNOWN) {
                    if (gestureInteraction != null) {
                        gestureInteraction.move(x, y);
                    }
                    return;
                }
                float scrollVertical = Math.abs(y - downY);
                float scrollHorizontal = Math.abs(x - downX);
                if (scrollVertical > THRESHOLD || scrollHorizontal > THRESHOLD) {
                    // TODO: Thumbs moves at an angle.  Adjust direction detection to take that into account.
                    if (scrollHorizontal > scrollVertical * DIRECTIONAL_MULTIPLIER) {
                        gesture = Gesture.DRAG_HORIZONTAL;
                        gestureInteraction = adapter.getHorizontalTouchInteraction(downX, downY);
                    } else {
                        gesture = Gesture.DRAG_VERTICAL;
                        gestureInteraction = adapter.getVerticalTouchInteraction(downX, downY);
                    }
                    if (gestureInteraction != null) {
                        gestureInteraction.move(x, y);
                    }
                }
            }

            @Override
            public void cancel() {
                if (gesture != Gesture.UNKNOWN) {
                    if (gestureInteraction != null) {
                        gestureInteraction.cancel();
                    }
                }
            }

            @Override
            public void up() {
                if (gesture == Gesture.UNKNOWN) {
                    TouchInteraction tapTouchInteraction = adapter.getTapTouchInteraction(downX, downY);
                    if (tapTouchInteraction == null) {
                        return;
                    }
                    tapTouchInteraction.up();
                    return;
                }

                if (gestureInteraction != null) {
                    gestureInteraction.up();
                }
            }
        };
    }

    public interface Adapter {
        TouchInteraction getHorizontalTouchInteraction(float downX, float downY);
        TouchInteraction getVerticalTouchInteraction(float downX, float downY);
        TouchInteraction getTapTouchInteraction(float downX, float downY);
    }

    enum Gesture {
        UNKNOWN,
        DRAG_HORIZONTAL,
        DRAG_VERTICAL
    }
}
