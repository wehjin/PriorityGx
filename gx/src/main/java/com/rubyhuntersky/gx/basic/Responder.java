package com.rubyhuntersky.gx.basic;

import com.rubyhuntersky.gx.interaction.KeypressInteraction;
import com.rubyhuntersky.gx.interaction.TouchInteraction;

/**
 * @author wehjin
 * @since 1/28/15.
 */

public interface Responder {
    TouchInteraction getTouchInteraction(float downX, float downY);
    KeypressInteraction getKeypressInteraction();
}
