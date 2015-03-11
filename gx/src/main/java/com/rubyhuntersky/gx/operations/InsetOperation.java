package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class InsetOperation {
    public static <Result> Interactive<Result> create(final Interactive<Result> interactive, final float widthFraction,
          final float heightFraction) {
        return CreateOperation.create(new OnPresent<Result>() {
            @Override
            public void call(Presenter<Result> presenter) {
                final RectF innerPerimeter = getInnerPerimeter(presenter.getUnits().getPerimeter(), widthFraction,
                      heightFraction);
                presenter.set(
                      interactive.present(presenter.getUnits().withPerimeter(innerPerimeter), presenter.getConsole(),
                            presenter.getSettings(), presenter.getMonitor()));
            }
        });
    }

    public static Interactive0 create0(final Interactive0 interactive0, final float widthFraction,
          final float heightFraction) {
        return CreateOperation.create0(new OnPresent<Void>() {
            @Override
            public void call(Presenter<Void> presenter) {
                final RectF innerPerimeter = getInnerPerimeter(presenter.getUnits().getPerimeter(), widthFraction,
                      heightFraction);
                presenter.set(
                      interactive0.present(presenter.getUnits().withPerimeter(innerPerimeter), presenter.getConsole(),
                            presenter.getSettings(), presenter.getMonitor()));
            }
        });
    }

    private static RectF getInnerPerimeter(RectF perimeter, float widthFraction, float heightFraction) {
        final float insetX = Math.round(widthFraction * perimeter.width());
        final float insetY = Math.round(heightFraction * perimeter.height());
        final RectF innerPerimeter = new RectF(perimeter);
        innerPerimeter.inset(insetX, insetY);
        return innerPerimeter;
    }
}
