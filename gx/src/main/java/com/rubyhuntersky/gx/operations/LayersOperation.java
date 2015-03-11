package com.rubyhuntersky.gx.operations;

import android.graphics.RectF;
import android.util.Log;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Units;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.support.Layer;
import com.rubyhuntersky.gx.support.LayersDynamics;
import com.rubyhuntersky.gx.support.PartsPresenter;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class LayersOperation {

    public static final String TAG = LayersOperation.class.getSimpleName();

    public static Interactive<LayersDynamics> create(final Layer... layers) {
        return CreateOperation.create(new OnPresent<LayersDynamics>() {
            @Override
            public void call(Presenter<LayersDynamics> presenter) {
                final PartsPresenter<Integer> parts = presenter.enableParts(Integer.class);

                final RectF outerPerimeter = presenter.getUnits().getPerimeter();
                final Units outerUnits = presenter.getUnits();
                final LayersDynamics outerDynamics = presenter.getSettings();
                final Monitor<? super LayersDynamics> outerMonitor = presenter.getMonitor();

                for (int i = 0; i < layers.length; i++) {
                    final PartsPresenter<Integer>.Part part = parts.getPart(i, i);

                    final Layer layer = layers[i];
                    final String name = layer.getName();
                    final RectF perimeter = layer.getRectInset().applyToRect(outerPerimeter);
                    final Units units = outerUnits.withPerimeter(perimeter);

                    final Console console = part.getConsole(true);
                    final Object dynamic = outerDynamics.get(name);
                    if (dynamic == null) {
                        Log.v(TAG, "Missing start dynamics for layer:" + name);
                    }
                    final Monitor monitor = new LayerMonitor(name, outerDynamics, outerMonitor);
                    final Interactive interactive = layer.getInteractive();

                    //noinspection unchecked
                    part.present(interactive, units, console, dynamic, monitor);
                }
            }

            class LayerMonitor implements Monitor {
                private final String name;
                private LayersDynamics outerDynamics;
                private final Monitor<? super LayersDynamics> outerMonitor;

                public LayerMonitor(String name, LayersDynamics outerDynamics,
                      Monitor<? super LayersDynamics> outerMonitor) {
                    this.name = name;
                    this.outerDynamics = outerDynamics;
                    this.outerMonitor = outerMonitor;
                }

                @Override
                public void onResult(Object result) {
                    final Object currentResult = outerDynamics.get(name);
                    if (currentResult.equals(result)) {
                        return;
                    }
                    outerDynamics = outerDynamics.withValue(name, result);
                    if (outerMonitor != null) {
                        outerMonitor.onResult(outerDynamics);
                    }
                }

                @Override
                public void onError(Throwable throwable) {
                    if (outerMonitor != null) {
                        outerMonitor.onError(throwable);
                    }
                }
            }
        });
    }

}
