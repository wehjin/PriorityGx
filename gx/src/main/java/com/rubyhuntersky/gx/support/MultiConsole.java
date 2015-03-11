package com.rubyhuntersky.gx.support;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.picture.Picture;

/**
 * @author wehjin
 * @since 2/11/15.
 */

public class MultiConsole<KeyT> {


    private final Console console;
    private MultiConsolePictureAttribute<KeyT> pictureAttribute;
    private MultiConsoleResponderAttribute<KeyT> responderAttribute;
    private MultiConsoleAnimatorAttribute<KeyT> animatorAttribute;

    public MultiConsole(Console console) {
        this.console = console;
        pictureAttribute = new MultiConsolePictureAttribute<>(console);
        responderAttribute = new MultiConsoleResponderAttribute<>(console);
        animatorAttribute = new MultiConsoleAnimatorAttribute<>(console);
    }

    public Console getSubconsole(final KeyT name, final int position, final boolean enableResponder) {
        return new Console() {

            @Override
            public void onPicture(Picture picture) {
                pictureAttribute.onContent(name, position, picture);
            }

            @Override
            public void onResponder(Responder responder) {
                if (enableResponder) {
                    responderAttribute.onContent(name, position, responder);
                }
            }

            @Override
            public <DialogT> void onDialog(Interactive<? extends DialogT> interactive, DialogT settings,
                  Monitor<? super DialogT> monitor) {
                console.onDialog(interactive, settings, monitor);
            }

            @Override
            public void onAnimator(Animator animator) {
                animatorAttribute.onContent(name, position, animator);
            }
        };
    }

    public void clear(KeyT key) {
        pictureAttribute.onContent(key, 0, null);
        responderAttribute.onContent(key, 0, null);
        animatorAttribute.onContent(key, 0, null);
    }
}
