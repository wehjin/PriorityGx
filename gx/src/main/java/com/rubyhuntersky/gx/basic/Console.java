package com.rubyhuntersky.gx.basic;

import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.support.Animator;

/**
 * @author wehjin
 * @since 1/28/15.
 */

public interface Console {
    void onPicture(Picture picture);
    void onResponder(Responder responder);
    <DialogT> void onDialog(Interactive<? extends DialogT> interactive, DialogT settings,
          Monitor<? super DialogT> monitor);
    void onAnimator(Animator animator);
}
