package com.rubyhuntersky.gx.shapes;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.basic.Interactives;
import com.rubyhuntersky.gx.basic.Presentations;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.picture.ColoredOvalPicture;

/**
 * @author wehjin
 * @since 2/16/15.
 */

public class OvalShape {
    public final int color;

    public OvalShape(int color) {
        this.color = color;
    }

    public static Interactive<OvalShape> getInteractive() {
        return Interactives.create(new OnPresent<OvalShape>() {
            @Override
            public void call(Presenter<OvalShape> presenter) {
                RectF perimeter = presenter.getUnits().getPerimeter();
                OvalShape dynamics = presenter.getSettings();
                ColoredOvalPicture picture = new ColoredOvalPicture(dynamics.color, perimeter);

                Console console = presenter.getConsole();
                console.onPicture(picture);
                presenter.set(Presentations.getClearPicture(console));
            }
        });
    }

    public static Interactive0 getInteractive0(OvalShape ovalShape) {
        return getInteractive().bind(ovalShape);
    }
}
