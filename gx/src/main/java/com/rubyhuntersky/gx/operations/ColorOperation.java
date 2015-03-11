package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.picture.ColoredRectanglePicture;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class ColorOperation {
    public static Interactive<Void> create(final int color) {
        return CreateOperation.create(new OnPresent<Void>() {
            @Override
            public void call(Presenter<Void> presenter) {
                final RectF perimeter = presenter.getUnits().getPerimeter();
                presenter.getConsole().onPicture(new ColoredRectanglePicture(color, perimeter));
            }
        });
    }
}
