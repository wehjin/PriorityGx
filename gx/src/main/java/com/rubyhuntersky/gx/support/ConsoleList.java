package com.rubyhuntersky.gx.support;

import com.rubyhuntersky.gx.basic.Console;
import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.console.BasicConsole;
import com.rubyhuntersky.gx.functions.Action1;
import com.rubyhuntersky.gx.interaction.KeypressInteraction;
import com.rubyhuntersky.gx.interaction.TouchInteraction;
import com.rubyhuntersky.gx.picture.Picture;
import com.rubyhuntersky.gx.picture.PictureList;

/**
 * @author wehjin
 * @since 1/29/15.
 */

public class ConsoleList {

    private final PictureList.Builder builder;
    private final Console console;
    private final Responder[] responders;
    private Responder broadcastResponder;


    public ConsoleList(Console console, int count) {
        this.console = console;
        this.builder = new PictureList.Builder(count);
        this.responders = new Responder[count];
    }


    public Console getConsole(final int index) {
        return new BasicConsole(console, new Action1<Responder>() {
            @Override
            public void call(Responder responder) {
                responders[index] = responder;
                if (broadcastResponder != null) {
                    return;
                }
                broadcastResponder = new Responder() {
                    @Override
                    public TouchInteraction getTouchInteraction(final float downX, final float downY) {
                        for (int i = responders.length - 1; i >= 0; i--) {
                            //TODO: Check if one of the higher-index responders covers over a low-index responder. If so the touch should never reach the low-index responder.
                            if (responders[i] != null) {
                                return responders[i].getTouchInteraction(downX, downY);
                            }
                        }
                        return null;
                    }

                    @Override
                    public KeypressInteraction getKeypressInteraction() {
                        return null;
                    }
                };
                console.onResponder(broadcastResponder);
            }
        }, new Action1<Picture>() {
            @Override
            public void call(Picture picture) {
                PictureList pictureList = builder.setPicture(index, picture);
                if (pictureList == null) {
                    // No change.
                    return;
                }
                if (pictureList.size() > 0) {
                    console.onPicture(pictureList);
                }
            }
        });
    }
}
