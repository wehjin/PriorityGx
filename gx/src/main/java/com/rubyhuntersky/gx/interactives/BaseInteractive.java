package com.rubyhuntersky.gx.interactives;

import android.util.Pair;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.operations.AdaptOperation;
import com.rubyhuntersky.gx.operations.AddLeftOperation;
import com.rubyhuntersky.gx.operations.CoverOperation;
import com.rubyhuntersky.gx.operations.EnableTapsOperation;
import com.rubyhuntersky.gx.operations.InsetMinOperation;
import com.rubyhuntersky.gx.operations.InsetOperation;
import com.rubyhuntersky.gx.support.Adapter;
import com.rubyhuntersky.gx.support.Adapter0;
import com.rubyhuntersky.gx.support.TapAction;

/**
 * @author wehjin
 * @since 1/29/15.
 */

public abstract class BaseInteractive<T> implements Interactive<T> {

    @Override
    public <OuterT> Interactive<OuterT> adapt(Adapter<T, OuterT> adapter) {
        return AdaptOperation.create(this, adapter);
    }

    @Override
    public Interactive0 bind(final T value) {
        return AdaptOperation.create0(this, new Adapter0<T>() {
            @Override
            public T getSettings() {
                return value;
            }
        });
    }

    @Override
    public <Cover> Interactive<Cover> overlay(Interactive<Cover> overlay, int inset, T underFill) {
        return CoverOperation.create(inset, overlay, underFill, this);
    }

    @Override
    public Interactive0 overlay(Interactive0 overlay, int inset, T underFill) {
        return CoverOperation.create(inset, overlay, underFill, this);
    }

    @Override
    public <Left> Interactive<Pair<T, Left>> addLeft(int width, Interactive<Left> left) {
        return AddLeftOperation.create(width, this, left);
    }

    @Override
    public Interactive<T> inset(float widthFraction, float heightFraction) {
        return InsetOperation.create(this, widthFraction, heightFraction);
    }

    @Override
    public Interactive<T> insetMin(float widthFraction, float heightFraction) {
        return InsetMinOperation.create(this, widthFraction, heightFraction);
    }

    @Override
    public Interactive<TapAction> enableTaps(TapAction tapAction) {
        return EnableTapsOperation.create(this, tapAction);
    }

    @Override
    public <Under> Interactive<T> underlay(Interactive<Under> underlay, int inset, Under underFill) {
        return underlay.overlay(this, inset, underFill);
    }
}
