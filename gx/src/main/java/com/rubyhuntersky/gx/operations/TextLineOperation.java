package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.picture.TextLinePicture;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class TextLineOperation {
    public static Interactive0 create0(final String text, final int color) {
        return create(color).bind(text);
    }

    public static Interactive<String> create(final int color) {
        return CreateOperation.create(new OnPresent<String>() {
            @Override
            public void call(Presenter<String> presenter) {
                String text = presenter.getSettings();
                RectF perimeter = presenter.getUnits().getPerimeter();
                presenter.getConsole().onPicture(new TextLinePicture(text, color, perimeter));
            }
        });
    }
}
