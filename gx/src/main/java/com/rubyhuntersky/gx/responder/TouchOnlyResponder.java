package com.rubyhuntersky.gx.responder;

import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.interaction.KeypressInteraction;

/**
 * @author wehjin
 * @since 2/20/15.
 */

abstract public class TouchOnlyResponder implements Responder {

    @Override
    public KeypressInteraction getKeypressInteraction() {
        return null;
    }
}
