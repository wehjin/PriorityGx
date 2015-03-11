package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.picture.ArrowToCenterPicture;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class ArrowToCenterShapeOperation {
    public static Interactive0 create(final int color) {
        return CreateOperation.create0(new OnPresent<Void>() {
            @Override
            public void call(Presenter<Void> presenter) {
                RectF perimeter = presenter.getUnits().getPerimeter();
                float penWidth = Math.round(Math.min(perimeter.width(), perimeter.height()) / 10f);
                presenter.getConsole().onPicture(new ArrowToCenterPicture(penWidth, perimeter, color));
            }
        });
    }
}
