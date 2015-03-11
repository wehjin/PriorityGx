package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;
import android.util.Pair;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Units;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.interactives.BaseInteractive;
import com.rubyhuntersky.gx.support.ConsolePair;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class AddLeftOperation {
    public static <Right, Left> Interactive<Pair<Right, Left>> create(final int width,
          final BaseInteractive<Right> right, final Interactive<Left> left) {

        return CreateOperation.create(new OnPresent<Pair<Right, Left>>() {
            @Override
            public void call(final Presenter<Pair<Right, Left>> presenter) {
                final ConsolePair consolePair = new ConsolePair(presenter.getConsole());
                final Units units = presenter.getUnits();
                RectF perimeter = units.getPerimeter();

                final RectF rightPerimeter = new RectF(perimeter);
                rightPerimeter.left += width;
                final Units rightUnits = units.withPerimeter(rightPerimeter);

                final RectF leftPerimeter = new RectF(perimeter);
                leftPerimeter.right = rightPerimeter.left;
                final Units leftUnits = units.withPerimeter(leftPerimeter);

                presenter.set(right.present(rightUnits, consolePair.getBackConsole(), null, null));
                presenter.set(left.present(leftUnits, consolePair.getFrontConsole(), null, null));
            }
        });
    }
}
