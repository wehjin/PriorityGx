package com.rubyhuntersky.gx.support;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.picture.PictureSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wehjin
 * @since 2/20/15.
 */
class MultiConsolePictureAttribute<KeyT> extends MultiConsoleAttribute<Picture, KeyT> {

    public static final Packager<Picture> PACKAGER = new Packager<Picture>() {
        @Override
        public Picture combine(Collection<Frame<Picture>> frames) {
            Set<PictureSet.Item> items = new HashSet<>(frames.size());
            for (Frame<Picture> frame : frames) {
                items.add(new PictureSet.Item(frame.position, frame.content));
            }
            return new PictureSet(items);
        }
    };
    public static final FrameMaker<Picture> FRAME_MAKER = new FrameMaker<Picture>() {
        @Override
        public Frame<Picture> make(int position, Picture content) {
            return new Frame<>(position, content);
        }
    };

    MultiConsolePictureAttribute(final Console console) {
        super(new Destination<Picture>() {
            @Override
            public void send(Picture content) {
                console.onPicture(content);
            }
        }, PACKAGER, FRAME_MAKER);
    }
}
