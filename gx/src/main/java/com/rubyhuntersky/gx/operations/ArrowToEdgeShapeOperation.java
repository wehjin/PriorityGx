package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.picture.ArrowToEdgePicture;

/**
 * @author wehjin
 * @since 2/15/15.
 */

public class ArrowToEdgeShapeOperation {

    public static Interactive0 create(final int color, final float sweep, final float stroke, final int azimuth) {
        return CreateOperation.create0(new OnPresent<Void>() {
            @Override
            public void call(Presenter<Void> presenter) {
                RectF perimeter = presenter.getUnits().getPerimeter();
                float penWidth = Math.round(Math.min(perimeter.width(), perimeter.height()) * stroke);
                presenter.getConsole().onPicture(new ArrowToEdgePicture(color, sweep, penWidth, perimeter, azimuth));
            }
        });
    }
}
