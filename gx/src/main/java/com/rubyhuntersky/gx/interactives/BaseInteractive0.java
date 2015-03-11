package com.rubyhuntersky.gx.interactives;

import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.operations.CoverOperation;
import com.rubyhuntersky.gx.operations.InsetOperation;

/**
 * @author wehjin
 * @since 2/16/15.
 */

abstract public class BaseInteractive0 extends BaseInteractive<Void> implements Interactive0 {

    @Override
    public Interactive0 inset0(float fraction) {
        return inset0(fraction, fraction);
    }

    @Override
    public Interactive0 inset0(float widthFraction, float heightFraction) {

        return InsetOperation.create0(this, widthFraction, heightFraction);
    }

    @Override
    public Interactive0 overlay0(Interactive0 overlay, int inset) {
        return CoverOperation.create0(inset, overlay, this);
    }
}
