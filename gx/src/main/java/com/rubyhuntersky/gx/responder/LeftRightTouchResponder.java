package com.rubyhuntersky.gx.responder;

import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.interaction.KeypressInteraction;
import com.rubyhuntersky.gx.interaction.TouchInteraction;

/**
 * @author wehjin
 * @since 2/8/15.
 */

public class LeftRightTouchResponder implements Responder {

    public static final float THRESHOLD = 25f;
    private final Adapter adapter;

    public LeftRightTouchResponder(Adapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public TouchInteraction getTouchInteraction(final float downX, final float downY) {
        return new TouchInteraction() {

            Movement movement = Movement.UNKNOWN;
            TouchInteraction innerTouchInteraction;

            @Override
            public void move(float x, float y) {
                if (innerTouchInteraction != null) {
                    innerTouchInteraction.move(x, y);
                    return;
                }

                if (movement != Movement.UNKNOWN) {
                    return;
                }

                float deltaX = x - downX;
                if (deltaX > THRESHOLD) {
                    movement = Movement.RIGHT;
                    innerTouchInteraction = adapter.getRightTouchInteraction(downX, downY);
                    innerTouchInteraction.move(x, y);
                    return;
                }

                if (deltaX < -THRESHOLD) {
                    movement = Movement.LEFT;
                    innerTouchInteraction = adapter.getLeftTouchInteraction(downX, downY);
                    innerTouchInteraction.move(x, y);
                    return;
                }

                if (Math.abs(y - downY) > THRESHOLD) {
                    movement = Movement.CANCELLED;
                }
            }

            @Override
            public void cancel() {
                if (innerTouchInteraction == null) {
                    return;
                }
                innerTouchInteraction.cancel();
            }

            @Override
            public void up() {
                if (innerTouchInteraction == null) {
                    // TODO Handle UNKNOWN as TAP.
                    return;
                }
                innerTouchInteraction.up();
            }
        };
    }

    @Override
    public KeypressInteraction getKeypressInteraction() {
        return null;
    }

    private enum Movement {
        UNKNOWN,
        LEFT,
        RIGHT,
        CANCELLED,
    }

    public interface Adapter {
        TouchInteraction getLeftTouchInteraction(float downX, float downY);
        TouchInteraction getRightTouchInteraction(float downX, float downY);
    }
}
