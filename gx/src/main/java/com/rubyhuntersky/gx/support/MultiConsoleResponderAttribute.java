package com.rubyhuntersky.gx.support;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.responder.ResponderSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wehjin
 * @since 2/20/15.
 */
class MultiConsoleResponderAttribute<KeyT> extends MultiConsoleAttribute<Responder, KeyT> {

    public static final Packager<Responder> PACKAGER = new Packager<Responder>() {
        @Override
        public Responder combine(Collection<Frame<Responder>> frames) {
            Set<ResponderSet.Item> items = new HashSet<>(frames.size());
            for (Frame<Responder> frame : frames) {
                items.add(new ResponderSet.Item(frame.position, frame.content));
            }
            return new ResponderSet(items);
        }
    };
    public static final FrameMaker<Responder> FRAME_MAKER = new FrameMaker<Responder>() {
        @Override
        public Frame<Responder> make(int position, Responder content) {
            return new Frame<>(position, content);
        }
    };

    MultiConsoleResponderAttribute(final Console console) {
        super(new Destination<Responder>() {
            @Override
            public void send(Responder content) {
                console.onResponder(content);
            }
        }, PACKAGER, FRAME_MAKER);
    }
}
