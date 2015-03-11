package com.rubyhuntersky.gx.picture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wehjin
 * @since 2/7/15.
 */

public class PictureSet extends Picture {

    public static final Comparator<Item> ITEM_COMPARATOR = new Comparator<Item>() {
        @Override
        public int compare(Item lhs, Item rhs) {
            if (lhs.position < rhs.position) {
                return -1;
            }
            if (lhs.position > rhs.position) {
                return 1;
            }
            return 0;
        }
    };

    public static class Item {
        public final int position;
        public Picture picture;

        public Item(int position, Picture picture) {
            this.position = position;
            this.picture = picture;
        }
    }

    private Set<Item> items = new HashSet<>();

    public PictureSet(Set<Item> items) {
        this.items = new HashSet<>(items);
    }

    public List<Item> getItems() {
        ArrayList<Item> itemList = new ArrayList<>(items);
        Collections.sort(itemList, ITEM_COMPARATOR);
        return itemList;
    }
}
