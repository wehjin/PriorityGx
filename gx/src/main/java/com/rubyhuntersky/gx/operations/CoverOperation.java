package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.basic.Units;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.support.PartsPresenter;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class CoverOperation {
    public static <Under, Over> Interactive<Over> create(final int inset, final Interactive<Over> overlay,
          final Under underFill, final Interactive<Under> underlay) {

        final Interactive0 underlay0 = underlay.bind(underFill);
        return CreateOperation.create(new OnPresent<>(underlay0, overlay, inset));
    }

    public static <Over> Interactive<Over> create(final int inset, final Interactive<Over> overlay,
          final Interactive0 underlay) {
        return CreateOperation.create(new OnPresent<>(underlay, overlay, inset));
    }

    public static <Under> Interactive0 create(final int inset, final Interactive0 overlay, final Under underFill,
          final Interactive<Under> underlay) {
        return CreateOperation.create0(new OnPresent<>(underlay.bind(underFill), overlay, inset));
    }

    public static Interactive0 create0(int inset, Interactive0 overlay, Interactive0 underlay) {
        return CreateOperation.create0(new OnPresent<>(underlay, overlay, inset));
    }


    private static class OnPresent<Over> implements com.rubyhuntersky.gx.creation.OnPresent<Over> {

        private final Interactive0 underlay0;
        private final Interactive<Over> overlay;
        private final int inset;

        public OnPresent(Interactive0 underlay0, Interactive<Over> overlay, int inset) {
            this.underlay0 = underlay0;
            this.overlay = overlay;
            this.inset = inset;
        }

        @Override
        public void call(final Presenter<Over> presenter) {
            PartsPresenter<String> parts = presenter.enableParts(String.class);
            Units units = presenter.getUnits();

            final RectF backingPerimeter = units.getPerimeter();
            final RectF coveringPerimeter = new RectF(backingPerimeter);
            coveringPerimeter.inset(inset, inset);
            final Units backingUnits = units.withPerimeter(backingPerimeter);
            final Units coveringUnits = units.withPerimeter(coveringPerimeter);

            PartsPresenter<String>.Part back = parts.getPart("back", 0);
            back.present(underlay0, backingUnits, back.getConsole(false), null, null);

            PartsPresenter<String>.Part front = parts.getPart("front", 1);
            front.present(overlay, coveringUnits, front.getConsole(true), presenter.getSettings(),
                  presenter.getMonitor());
        }
    }
}
