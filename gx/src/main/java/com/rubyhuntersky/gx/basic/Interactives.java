package com.rubyhuntersky.gx.basic;

import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.operations.ArrowToCenterShapeOperation;
import com.rubyhuntersky.gx.operations.ArrowToEdgeShapeOperation;
import com.rubyhuntersky.gx.operations.ColorOperation;
import com.rubyhuntersky.gx.operations.CreateOperation;
import com.rubyhuntersky.gx.operations.CrossShapeOperation;
import com.rubyhuntersky.gx.operations.LayersOperation;
import com.rubyhuntersky.gx.operations.TextLineOperation;
import com.rubyhuntersky.gx.operations.TextMultiLineOperation;
import com.rubyhuntersky.gx.support.EditorStatus;
import com.rubyhuntersky.gx.support.Layer;
import com.rubyhuntersky.gx.support.LayersDynamics;

import java.util.List;

/**
 * @author wehjin
 * @since 1/27/15.
 */

public class Interactives {

    public static <T> Interactive<T> create(final OnPresent<T> onPresent) {
        return CreateOperation.create(onPresent);
    }

    public static <T> Interactive<T> echo() {
        return create(new OnPresent<T>() {
            @Override
            public void call(Presenter<T> presenter) {
                presenter.getMonitor().onResult(presenter.getSettings());
            }
        });
    }

    public static Interactive<Void> color(final int color) {
        return ColorOperation.create(color);
    }

    public static Interactive<Void> crossShape(int color) {
        return CrossShapeOperation.create(color);
    }

    public static Interactive0 arrowToCenterShape(int color) {
        return ArrowToCenterShapeOperation.create(color);
    }

    public static Interactive0 arrowToEdgeShape(int color, float sweep, float stroke) {
        return arrowToEdgeShape(color, sweep, stroke, 6);
    }

    public static Interactive0 arrowToEdgeShape(int color, float sweep, float stroke, int azimuth) {
        return ArrowToEdgeShapeOperation.create(color, sweep, stroke, azimuth);
    }

    public static Interactive0 textLine(final String text, final int color) {
        return TextLineOperation.create0(text, color);
    }

    public static Interactive<String> textLine(final int color) {
        return TextLineOperation.create(color);
    }

    public static Interactive0 textMultiLine(final String text, final int color, final int lineCount) {
        return TextMultiLineOperation.create(text, color, lineCount);
    }

    public static Interactive<String> textMultiLine(final int color, final int lineCount) {
        return TextMultiLineOperation.create(color, lineCount);
    }

    public static Interactive<LayersDynamics> layers(final Layer... layers) {
        return LayersOperation.create(layers);
    }

    public static Interactive<LayersDynamics> layers(List<? extends Layer<?>> layers) {
        return LayersOperation.create(layers.toArray(new Layer[layers.size()]));
    }

    public static <Value> Interactive<EditorStatus<Value>> nativeEditor() {
        return create(new OnPresent<EditorStatus<Value>>() {
            @Override
            public void call(Presenter<EditorStatus<Value>> presenter) {
                // Immediately post result so the native editor can discover its type and value.
                final EditorStatus<Value> settings = presenter.getSettings();
                presenter.getMonitor().onResult(new EditorStatus<>(settings));
            }
        });
    }
}
