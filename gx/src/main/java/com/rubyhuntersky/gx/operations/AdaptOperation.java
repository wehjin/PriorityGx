package com.rubyhuntersky.gx.operations;

import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.support.Adapter;
import com.rubyhuntersky.gx.support.Adapter0;

/**
 * @author wehjin
 * @since 2/14/15.
 */

public class AdaptOperation {
    public static <Outer, Inner> Interactive<Outer> create(final Interactive<Inner> inner,
          final Adapter<Inner, Outer> adapter) {
        return CreateOperation.create(new OnPresent<Outer>() {
            @Override
            public void call(final Presenter<Outer> presenter) {
                presenter.set(inner.present(presenter.getUnits(), presenter.getConsole(),
                      adapter.getSettings(presenter.getSettings()),
                      presenter.getMonitor() == null ? null : new Monitor<Inner>() {
                          @Override
                          public void onResult(Inner result) {
                              presenter.getMonitor().onResult(adapter.getResult(result));
                          }

                          @Override
                          public void onError(Throwable throwable) {
                              presenter.getMonitor().onError(throwable);
                          }
                      }));
            }
        });
    }

    public static <Inner> Interactive0 create0(final Interactive<Inner> inner, final Adapter0<Inner> adapter0) {
        return CreateOperation.create0(new OnPresent<Void>() {
            @Override
            public void call(Presenter<Void> presenter) {
                presenter.set(
                      inner.present(presenter.getUnits(), presenter.getConsole(), adapter0.getSettings(), null));
            }
        });
    }
}
