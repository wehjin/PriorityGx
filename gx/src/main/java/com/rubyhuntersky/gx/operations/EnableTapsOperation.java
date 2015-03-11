package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Presentation;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.console.BasicConsole;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.functions.Action1;
import com.rubyhuntersky.gx.interaction.KeypressInteraction;
import com.rubyhuntersky.gx.interaction.TouchInteraction;
import com.rubyhuntersky.gx.support.TapAction;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class EnableTapsOperation implements Responder {

    public static final float MOVE_THRESHOLD = 25f;
    private final RectF perimeter;
    private final Monitor<? super TapAction> monitor;
    private final TapAction tapAction;

    private EnableTapsOperation(RectF perimeter, Monitor<? super TapAction> monitor, TapAction tapAction) {
        this.perimeter = perimeter;
        this.monitor = monitor;
        this.tapAction = tapAction;
    }


    @Override
    public TouchInteraction getTouchInteraction(final float downX, final float downY) {
        if (!perimeter.contains(downX, downY)) {
            return null;
        }
        return new TouchInteraction() {

            boolean isTap = true;

            @Override
            public void move(float x, float y) {
                // If tap is gone, don't need to check further moves.
                if (!isTap) {
                    return;
                }
                // Remove tap if the touch moves too far from the start.
                if (Math.abs(x - downX) > MOVE_THRESHOLD || Math.abs(y - downY) > MOVE_THRESHOLD) {
                    isTap = false;
                }
            }

            @Override
            public void cancel() {
                isTap = false;
            }

            @Override
            public void up() {
                if (!isTap) {
                    return;
                }

                monitor.onResult(tapAction);
            }
        };
    }

    @Override
    public KeypressInteraction getKeypressInteraction() {
        return null;
    }

    public static Interactive<TapAction> create(final Interactive<?> interactive, final TapAction tapAction) {
        return CreateOperation.create(new OnPresent<TapAction>() {
            @Override
            public void call(final Presenter<TapAction> presenter) {
                final RectF perimeter = presenter.getUnits().getPerimeter();

                final Presentation presentation = interactive.present(presenter.getUnits(),
                      new BasicConsole(presenter.getConsole(), new Action1<Responder>() {
                          @Override
                          public void call(Responder responder) {
                              // Ignore inner presentation responder.  We'll deliver our own tap-detecting responder.
                          }
                      }), null, null);
                presenter.set(presentation);
                presenter.getConsole()
                         .onResponder(new EnableTapsOperation(perimeter, presenter.getMonitor(), tapAction));
            }
        });
    }
}
