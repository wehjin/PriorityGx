package com.rubyhuntersky.gx.picture;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wehjin
 * @since 1/29/15.
 */

public class PictureList extends Picture {

    private List<Picture> pictures;

    private PictureList(int rearIndex, Picture... pictures) {
        this.pictures = new ArrayList<>(pictures.length);
        if (rearIndex >= 0 && pictures.length > rearIndex) {
            this.pictures.add(pictures[rearIndex]);
        }
        for (int index = 0; index < pictures.length; index++) {
            if (index == rearIndex) {
                continue;
            }
            Picture picture = pictures[index];
            if (picture != null) {
                this.pictures.add(picture);
            }
        }
    }

    public int size() {
        return pictures.size();
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public static class Builder {

        private int rearIndex;
        private Picture[] pictures;

        public Builder(int count, int rearIndex) {
            pictures = new Picture[count];
            this.rearIndex = rearIndex;
        }

        public Builder(int count) {
            this(count, -1);
        }

        public void setCount(int count) {
            pictures = new Picture[count];
        }

        public PictureList setPicture(int index, Picture picture) {
            if (pictures[index] == picture) {
                return null;
            }
            pictures[index] = picture;
            return new PictureList(rearIndex, pictures);
        }

        public void setRearIndex(int rearIndex) {
            this.rearIndex = rearIndex;
        }
    }
}
