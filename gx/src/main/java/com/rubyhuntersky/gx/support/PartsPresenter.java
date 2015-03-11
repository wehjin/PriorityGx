package com.rubyhuntersky.gx.support;

import android.support.annotation.NonNull;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Interactive0;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Presentation;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.basic.Units;
import com.rubyhuntersky.gx.creation.PresentationGroup;
import com.rubyhuntersky.gx.picture.Picture;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wehjin
 * @since 2/13/15.
 */

public class PartsPresenter<KeyT> {

    private final Class<KeyT> cls;
    private final MultiConsole<KeyT> multiConsole;
    private final Map<KeyT, Presentation> presentations = new HashMap<>(2);
    private boolean isDismissed;

    public PartsPresenter(final PresentationGroup presentationGroup, final Console console, Class<KeyT> cls) {
        this.cls = cls;
        multiConsole = new MultiConsole<>(new Console() {
            @Override
            public void onPicture(Picture picture) {
                if (!isDismissed) {
                    console.onPicture(picture);
                }
            }

            @Override
            public void onResponder(Responder responder) {
                if (!isDismissed) {
                    console.onResponder(responder);
                }
            }

            @Override
            public <DialogT> void onDialog(Interactive<? extends DialogT> interactive, DialogT settings,
                  Monitor<? super DialogT> monitor) {
                if (!isDismissed) {
                    console.onDialog(interactive, settings, monitor);
                }
            }

            @Override
            public void onAnimator(Animator animator) {
                if (!isDismissed) {
                    console.onAnimator(animator);
                }
            }
        });
        presentationGroup.set(new Presentation() {
            @Override
            public boolean isDismissed() {
                return isDismissed;
            }

            @Override
            public void dismiss() {
                if (isDismissed()) {
                    return;
                }

                console.onPicture(null);
                console.onResponder(null);
                // TODO: onDialog too?

                // Set dismissed before dismissing inner presentations to block multi-console from sending pictures
                // and responders.
                isDismissed = true;
                clear();
            }
        });
    }

    public <CheckT> PartsPresenter<CheckT> checkType(Class<CheckT> keyClass) {
        if (!this.cls.isAssignableFrom(keyClass)) {
            throw new IllegalArgumentException("Incompatible key class");
        }
        //noinspection unchecked
        return (PartsPresenter<CheckT>) this;
    }

    public void clear() {
        for (Presentation presentation : presentations.values()) {
            presentation.dismiss();
        }
        presentations.clear();
    }

    private void clear(KeyT key) {
        Presentation previous = presentations.get(key);
        if (previous != null) {
            previous.dismiss();
            presentations.remove(key);
        }
    }

    private <T> void present(final KeyT key, @NonNull Interactive<T> interactive, Units units, Console console, T start,
          Monitor<? super T> monitor) {

        // Dismiss previous presentation BEFORE presenting the new interactive.  If the previous is dismissed later,
        // the dismissal will overwrite pictures delivered by the new presentation when it passes null to onSubpicture.
        clear(key);

        final Presentation presentation = interactive.present(units, console, start, monitor);
        presentations.put(key, new Presentation() {
            @Override
            public boolean isDismissed() {
                return presentation.isDismissed();
            }

            @Override
            public void dismiss() {
                if (isDismissed()) {
                    return;
                }
                multiConsole.clear(key);
                presentation.dismiss();
            }
        });
    }

    public Part getPart(KeyT key, int position) {
        return new Part(key, position);
    }

    public class Part {
        private final KeyT key;
        private final int position;

        private Part(KeyT key, int position) {
            this.key = key;
            this.position = position;
        }

        public Console getConsole(boolean enableResponder) {
            return multiConsole.getSubconsole(key, position, enableResponder);
        }

        public <T> void present(@NonNull Interactive<T> interactive, Units units, Console console, T start,
              Monitor<? super T> monitor) {
            PartsPresenter.this.present(key, interactive, units, console, start, monitor);
        }

        public void present0(@NonNull Interactive0 interactive0, Units units, Console console) {
            PartsPresenter.this.present(key, interactive0, units, console, null, null);
        }

        public void clear() {
            PartsPresenter.this.clear(key);
        }
    }
}
