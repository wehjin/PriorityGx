package com.rubyhuntersky.gx.support;

import android.support.annotation.NonNull;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactives;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wehjin
 * @since 1/30/15.
 */

public class Layer<T> {
    private final RectInset rectInset;
    private final Interactive<T> interactive;
    private final String name;

    public Layer(Interactive<T> interactive, RectInset rectInset, @NonNull String name) {
        this.rectInset = rectInset == null ? new RectInset.Exact(0, 0, 0, 0) : rectInset;
        this.interactive = interactive;
        this.name = name;
    }

    public Layer(Interactive<T> interactive, float insetLeft, float insetTop, float insetRight, float insetBottom,
          String name) {
        this(interactive, new RectInset.Exact(insetLeft, insetTop, insetRight, insetBottom), name);
    }

    public Layer(Interactive<T> interactive, @NonNull String name) {
        this(interactive, null, name);
    }

    public RectInset getRectInset() {
        return rectInset;
    }

    public Interactive<T> getInteractive() {
        return interactive;
    }

    public String getName() {
        return name;
    }

    public Builder add(Layer nextLayer) {
        return new Builder(this, nextLayer);
    }


    public static class Builder {

        private final List<Layer> layers;

        public Builder(Layer firstLayer, Layer secondLayer) {
            layers = new ArrayList<>(2);
            layers.add(firstLayer);
            layers.add(secondLayer);
        }

        public Builder add(Layer nextLayer) {
            layers.add(nextLayer);
            return this;
        }

        public Layer[] toArray() {
            Layer[] array = new Layer[layers.size()];
            return layers.toArray(array);
        }

        public Interactive<LayersDynamics> toLayers() {
            return Interactives.layers(toArray());
        }
    }
}
