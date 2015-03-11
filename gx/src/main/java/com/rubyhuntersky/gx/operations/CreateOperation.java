package com.rubyhuntersky.gx.operations;

import android.support.annotation.Nullable;
import android.util.Log;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Presentation;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.basic.Units;
import com.rubyhuntersky.gx.creation.OnPresent;
import com.rubyhuntersky.gx.creation.Presenter;
import com.rubyhuntersky.gx.interactives.BaseInteractive;
import com.rubyhuntersky.gx.interactives.BaseInteractive0;
import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.support.Animator;
import com.rubyhuntersky.gx.support.PartsPresenter;

/**
 * @author wehjin
 * @since 2/6/15.
 */

public class CreateOperation<T> implements Presenter<T> {

    public static final String TAG = CreateOperation.class.getSimpleName();
    private final Console console;
    private final Units units;
    private final T dynamics;
    private final Monitor<? super T> monitor;
    private boolean isDismissed;
    private Presentation presentation;
    private PartsPresenter<?> partsPresenter;


    public CreateOperation(T dynamics, Units units, Console console, Monitor<? super T> monitor) {
        this.console = console;
        this.units = units;
        this.dynamics = dynamics;
        this.monitor = monitor;
    }

    @Override
    public void set(final Presentation presentation) {
        if (isDismissed) {
            if (presentation != null) {
                presentation.dismiss();
            }
            return;
        }
        if (this.presentation != null) {
            this.presentation.dismiss();
        }
        this.presentation = presentation;
    }

    @Override
    public <KeyT> PartsPresenter<KeyT> enableParts(Class<KeyT> keyClass) {
        if (partsPresenter == null) {
            if (presentation != null) {
                throw new IllegalStateException("enableParts must be called before set");
            }
            partsPresenter = new PartsPresenter<>(this, console, keyClass);
        }
        return partsPresenter.checkType(keyClass);
    }

    @Override
    public boolean isDismissed() {
        return isDismissed;
    }

    @Override
    public void dismiss() {
        if (isDismissed) {
            return;
        }
        set(null);
        isDismissed = true;
    }

    @Override
    public Units getUnits() {
        return units;
    }

    @Override
    public Console getConsole() {
        if (partsPresenter != null) {
            Log.w(TAG, "getConsole called after enableParts.");
        }
        return new CheckDismissedConsole();
    }

    @Override
    public T getSettings() {
        return dynamics;
    }

    @Override
    public Monitor<? super T> getMonitor() {
        if (monitor == null) {
            return null;
        }
        return new Monitor<T>() {
            @Override
            public void onResult(T result) {
                if (isDismissed) {
                    return;
                }
                monitor.onResult(result);
            }

            @Override
            public void onError(Throwable throwable) {
                if (isDismissed) {
                    return;
                }
                monitor.onError(throwable);
            }
        };
    }

    public static <T> Interactive<T> create(final OnPresent<T> onPresent) {
        return new BaseInteractive<T>() {

            @Override
            public Presentation present(Units units, final Console console, T settings,
                  @Nullable final Monitor<? super T> monitor) {
                Presenter<T> presenter = new CreateOperation<>(settings, units, console, monitor);
                onPresent.call(presenter);
                return presenter;
            }
        };
    }

    public static Interactive0 create0(final OnPresent<Void> onPresent) {
        return new BaseInteractive0() {
            @Override
            public Presentation present(Units units, Console console, Void settings,
                  @Nullable Monitor<? super Void> monitor) {
                Presenter<Void> presenter = new CreateOperation<>(settings, units, console, monitor);
                onPresent.call(presenter);
                return presenter;
            }
        };
    }

    private class CheckDismissedConsole implements Console {
        @Override
        public void onPicture(Picture picture) {
            if (isDismissed) {
                return;
            }
            console.onPicture(picture);
        }

        @Override
        public void onResponder(Responder responder) {
            if (isDismissed) {
                return;
            }
            console.onResponder(responder);
        }

        @Override
        public <DialogT> void onDialog(Interactive<? extends DialogT> interactive, DialogT settings,
              Monitor<? super DialogT> monitor) {
            if (isDismissed) {
                return;
            }
            console.onDialog(interactive, settings, monitor);
        }

        @Override
        public void onAnimator(Animator animator) {
            if (isDismissed) {
                return;
            }
            console.onAnimator(animator);
        }
    }
}
