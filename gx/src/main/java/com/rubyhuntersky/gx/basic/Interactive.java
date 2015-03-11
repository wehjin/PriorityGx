package com.rubyhuntersky.gx.basic;

import android.support.annotation.Nullable;
import android.util.Pair;

import com.rubyhuntersky.gx.support.Adapter;
import com.rubyhuntersky.gx.support.TapAction;

/**
 * @author wehjin
 * @since 1/27/15.
 */

public interface Interactive<T> {

    Presentation present(Units units, Console console, T settings, @Nullable Monitor<? super T> monitor);

    Interactive0 bind(T value);

    <OuterT> Interactive<OuterT> adapt(Adapter<T, OuterT> adapter);

    // Test Horizon

    <Over> Interactive<Over> overlay(Interactive<Over> overlay, int inset, T underFill);

    Interactive0 overlay(Interactive0 overlay, int inset, T underFill);

    <Under> Interactive<T> underlay(Interactive<Under> underlay, int inset, Under underFill);

    <Left> Interactive<Pair<T, Left>> addLeft(int width, Interactive<Left> left);

    Interactive<T> inset(float widthFraction, float heightFraction);

    Interactive<T> insetMin(float widthFraction, float heightFraction);

    Interactive<TapAction> enableTaps(TapAction tapAction);
}
