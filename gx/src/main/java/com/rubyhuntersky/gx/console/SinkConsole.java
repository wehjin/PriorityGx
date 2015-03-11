package com.rubyhuntersky.gx.console;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Interactive;
import com.rubyhuntersky.gx.basic.Monitor;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.support.Animator;

/**
 * @author wehjin
 * @since 2/9/15.
 */

public class SinkConsole implements Console {
    @Override
    public void onPicture(Picture picture) {
        // Do nothing
    }

    @Override
    public void onResponder(Responder responder) {
        // Do nothing
    }

    @Override
    public <DialogResult> void onDialog(Interactive<? extends DialogResult> interactive, DialogResult settings,
          Monitor<? super DialogResult> monitor) {
        // Do nothing
    }

    @Override
    public void onAnimator(Animator animator) {
        // Do nothing
    }
}
