package com.rubyhuntersky.gx.support;

import com.rubyhuntersky.gx.basic.Console;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author wehjin
 * @since 2/20/15.
 */
class MultiConsoleAnimatorAttribute<KeyT> extends MultiConsoleAttribute<Animator, KeyT> {

    public static final Packager<Animator> PACKAGER = new Packager<Animator>() {
        @Override
        public Animator combine(Collection<Frame<Animator>> frames) {
            final List<Animator> animators = new ArrayList<>(frames.size());
            for (Frame<Animator> frame : frames) {
                animators.add(frame.content);
            }
            return new Animator() {
                @Override
                public void onAnimationFrame() {
                    for (Animator animator : animators) {
                        animator.onAnimationFrame();
                    }
                }
            };
        }
    };
    public static final FrameMaker<Animator> FRAME_MAKER = new FrameMaker<Animator>() {
        @Override
        public Frame<Animator> make(int position, Animator content) {
            return new Frame<>(position, content);
        }
    };

    MultiConsoleAnimatorAttribute(final Console console) {
        super(new Destination<Animator>() {
            @Override
            public void send(Animator content) {
                console.onAnimator(content);
            }
        }, PACKAGER, FRAME_MAKER);
    }
}
