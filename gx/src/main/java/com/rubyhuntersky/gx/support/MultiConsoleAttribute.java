package com.rubyhuntersky.gx.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wehjin
 * @since 2/20/15.
 */
abstract class MultiConsoleAttribute<T, KeyT> {

    private final Runnable onChanged = new Runnable() {
        @Override
        public void run() {
            postOuterContent(frameMap.values(), packager);
        }
    };
    private final Destination<T> destination;
    private final Packager<T> packager;
    private final FrameMaker<T> frameMaker;
    private final Map<KeyT, Frame<T>> frameMap = new HashMap<>(2);

    public MultiConsoleAttribute(Destination<T> destination, Packager<T> packager, FrameMaker<T> frameMaker) {
        this.destination = destination;
        this.packager = packager;
        this.frameMaker = frameMaker;
    }

    public void onContent(KeyT name, int position, T content) {
        if (content == null) {
            clearFrame(name);
            return;
        }
        addFrame(name, position, content);
    }

    private void addFrame(KeyT name, int position, T content) {
        final Frame<T> frame = frameMap.get(name);
        if (frame != null && frame.position == position && frame.content == content) {
            return;
        }
        Frame<T> newFrame = frameMaker.make(position, content);
        frameMap.put(name, newFrame);
        onChanged.run();
    }

    private void clearFrame(KeyT name) {
        Frame<T> frame = frameMap.get(name);
        if (frame == null) {
            return;
        }
        frameMap.remove(name);
        onChanged.run();
    }

    private void postOuterContent(Collection<Frame<T>> frames, Packager<T> packager) {
        int frameCount = frames.size();

        if (frameCount == 0) {
            destination.send(null);
            return;
        }

        if (frameCount == 1) {
            for (Frame<T> frame : frames) {
                destination.send(frame.content);
            }
            return;
        }

        T combine = packager.combine(frames);
        destination.send(combine);
    }

    public interface Destination<T> {
        void send(T content);
    }

    public interface Packager<T> {
        T combine(Collection<Frame<T>> frames);
    }

    public interface FrameMaker<T> {
        Frame<T> make(int position, T content);
    }

    public static class Frame<T> {
        public final int position;
        public final T content;

        public Frame(int position, T content) {
            this.position = position;
            this.content = content;
        }
    }
}
