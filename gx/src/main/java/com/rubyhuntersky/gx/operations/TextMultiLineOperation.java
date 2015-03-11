package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.picture.TextMultiLinePicture;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class TextMultiLineOperation {

    public static Interactive0 create(final String text, final int color, final int lineCount) {
        return create(color, lineCount).bind(text);
    }

    public static Interactive<String> create(final int color, final int lineCount) {
        return CreateOperation.create(new OnPresent<String>() {
            @Override
            public void call(Presenter<String> presenter) {
                String text = presenter.getSettings();
                RectF perimeter = presenter.getUnits().getPerimeter();
                TextMultiLinePicture picture = new TextMultiLinePicture(text, color, lineCount, perimeter);
                presenter.getConsole().onPicture(picture);
            }
        });
    }
}
