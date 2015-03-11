package com.rubyhuntersky.gx.picture;

import java.util.List;

/**
 * @author wehjin
 * @since 1/29/15.
 */

public class PicturePair extends Picture {

    PictureList pictureList;

    private PicturePair(PictureList pictureList) {
        this.pictureList = pictureList;
    }

    public Picture getBackground() {
        List<Picture> pictures = pictureList.getPictures();
        return pictures.size() > 0 ? pictures.get(0) : null;
    }

    public Picture getForeground() {
        List<Picture> pictures = pictureList.getPictures();
        return pictures.size() > 1 ? pictures.get(1) : null;
    }

    public static class Builder extends PictureList.Builder {

        public Builder() {
            super(2);
        }

        public PicturePair setBackground(Picture background) {
            return new PicturePair(super.setPicture(0, background));
        }

        public PicturePair setForeground(Picture foreground) {
            return new PicturePair(super.setPicture(1, foreground));
        }
    }
}
