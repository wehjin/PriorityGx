package com.rubyhuntersky.gx.console;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.functions.Action1;
import com.rubyhuntersky.gx.functions.Action2;
import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.support.Animator;

/**
 * @author wehjin
 * @since 2/9/15.
 */

public class BasicConsole implements Console {

    private final Console lowerConsole;
    private final Action1<Responder> responderAction;
    private final Action1<Picture> pictureAction;
    private final Action2<? super Interactive<?>, ? super Monitor<?>> dialogAction;

    public <Dialog> BasicConsole(Console lowerConsole, Action1<Responder> onResponder, Action1<Picture> onPicture,
                                 Action2<? super Interactive<? extends Dialog>, ? super Monitor<? super Dialog>> onDialog) {
        this.lowerConsole = lowerConsole;
        this.responderAction = onResponder;
        this.pictureAction = onPicture;

        //noinspection unchecked
        this.dialogAction = (Action2<? super Interactive<?>, ? super Monitor<?>>) onDialog;
    }

    public BasicConsole(Console lowerConsole, Action1<Responder> onResponder, Action1<Picture> onPicture) {
        this(lowerConsole, onResponder, onPicture, null);
    }

    public BasicConsole(Console lowerConsole, Action1<Responder> onResponder) {
        this(lowerConsole, onResponder, null, null);
    }

    @Override
    public void onPicture(Picture picture) {
        if (pictureAction != null) {
            pictureAction.call(picture);
            return;
        }
        lowerConsole.onPicture(picture);
    }

    @Override
    public void onResponder(Responder responder) {
        if (responderAction != null) {
            responderAction.call(responder);
            return;
        }
        lowerConsole.onResponder(responder);
    }

    @Override
    public <T> void onDialog(Interactive<? extends T> interactive, T settings, Monitor<? super T> monitor) {
        if (dialogAction != null) {
            dialogAction.call(interactive, monitor);
            return;
        }
        lowerConsole.onDialog(interactive, settings, monitor);
    }

    @Override
    public void onAnimator(Animator animator) {
        lowerConsole.onAnimator(animator);
    }
}
