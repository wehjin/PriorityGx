package com.rubyhuntersky.gx.responder;

import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.functions.Function0;
import com.rubyhuntersky.gx.functions.Function2;
import com.rubyhuntersky.gx.interaction.KeypressInteraction;
import com.rubyhuntersky.gx.interaction.TouchInteraction;

/**
 * @author wehjin
 * @since 3/7/15.
 */

public class FunctionResponder implements Responder {

    private final Function2<Float, Float, TouchInteraction> touchFunction;
    private final Function0<KeypressInteraction> keyFunction;

    public FunctionResponder(Function2<Float, Float, TouchInteraction> touchFunction,
          Function0<KeypressInteraction> keyFunction) {
        this.touchFunction = touchFunction;
        this.keyFunction = keyFunction;
    }

    @Override
    public TouchInteraction getTouchInteraction(float downX, float downY) {
        return touchFunction.call(downX, downY);
    }

    @Override
    public KeypressInteraction getKeypressInteraction() {
        return keyFunction.call();
    }
}
