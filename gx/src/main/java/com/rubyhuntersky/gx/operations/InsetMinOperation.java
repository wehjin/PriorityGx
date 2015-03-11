package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Units;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class InsetMinOperation {
    public static <Result> Interactive<Result> create(final Interactive<Result> interactive, final float widthFraction,
                                                      final float heightFraction) {
        return CreateOperation.create(new OnPresent<Result>() {
            @Override
            public void call(Presenter<Result> presenter) {
                final Units units = presenter.getUnits();
                final RectF perimeter = units.getPerimeter();
                final float inset = getMinimumInset(perimeter, widthFraction, heightFraction);

                final RectF innerPerimeter = new RectF(perimeter);
                innerPerimeter.inset(inset, inset);
                final Units innerUnits = units.withPerimeter(innerPerimeter);

                presenter.set(interactive.present(innerUnits, presenter.getConsole(), null, presenter.getMonitor()));
            }
        });
    }

    private static float getMinimumInset(RectF perimeter, float widthFraction, float heightFraction) {
        final float widthInset = Math.round(widthFraction * perimeter.width());
        final float heightInset = Math.round(heightFraction * perimeter.height());
        return Math.min(widthInset, heightInset);
    }
}
