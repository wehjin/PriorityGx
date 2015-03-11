package com.rubyhuntersky.gx.support;

import java.util.HashMap;

/**
 * @author wehjin
 * @since 2/13/15.
 */

public class LayersDynamics extends HashMap<String, Object> {
    public LayersDynamics() {
    }

    public LayersDynamics(String key, Object value) {
        put(key, value);
    }

    public LayersDynamics(LayersDynamics start, String name, Object value) {
        putAll(start);
        put(name, value);
    }

    public LayersDynamics withValue(String name, Object value) {
        return new LayersDynamics(this, name, value);
    }
}
