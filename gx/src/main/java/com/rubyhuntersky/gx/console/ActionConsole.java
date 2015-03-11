package com.rubyhuntersky.gx.console;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.functions.Action1;
import com.rubyhuntersky.gx.functions.Action3;
import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.support.Animator;

/**
 * @author wehjin
 * @since 3/7/15.
 */

public class ActionConsole implements Console {

    final Action1<Picture> pictureAction;
    final Action1<Responder> responderAction;
    final Action3<Interactive<?>, Object, Monitor<?>> dialogAction;
    final Action1<Animator> animatorAction;

    public ActionConsole(Action1<Picture> pictureAction, Action1<Responder> responderAction,
          Action3<Interactive<?>, Object, Monitor<?>> dialogAction, Action1<Animator> animatorAction) {
        this.pictureAction = pictureAction;
        this.responderAction = responderAction;
        this.dialogAction = dialogAction;
        this.animatorAction = animatorAction;
    }

    @Override
    public void onPicture(Picture picture) {
        pictureAction.call(picture);
    }

    @Override
    public void onResponder(Responder responder) {
        responderAction.call(responder);
    }

    @Override
    public <DialogT> void onDialog(Interactive<? extends DialogT> interactive, DialogT settings,
          Monitor<? super DialogT> monitor) {
        dialogAction.call(interactive, settings, monitor);
    }

    @Override
    public void onAnimator(Animator animator) {
        animatorAction.call(animator);
    }
}
