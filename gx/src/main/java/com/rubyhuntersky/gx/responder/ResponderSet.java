package com.rubyhuntersky.gx.responder;

import com.rubyhuntersky.gx.basic.Responder;
import com.rubyhuntersky.gx.interaction.KeypressInteraction;
import com.rubyhuntersky.gx.interaction.TouchInteraction;

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

public class ResponderSet implements Responder {

    public static final Comparator<Item> ASCENDING_POSITION = new Comparator<Item>() {
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
    public static final Comparator<Item> DESCENDING_POSITION = Collections.reverseOrder(ASCENDING_POSITION);

    public static class Item {
        public final int position;
        public Responder responder;

        public Item(int position, Responder responder) {
            this.position = position;
            this.responder = responder;
        }
    }

    private Set<Item> items = new HashSet<>();

    public ResponderSet(Set<Item> items) {
        this.items = new HashSet<>(items);
    }

    public List<Item> getItemsInDescendingOrder() {
        ArrayList<Item> itemList = new ArrayList<>(items);
        Collections.sort(itemList, DESCENDING_POSITION);
        return itemList;
    }

    @Override
    public TouchInteraction getTouchInteraction(float downX, float downY) {
        List<Item> itemsInDescendingOrder = getItemsInDescendingOrder();
        for (Item item : itemsInDescendingOrder) {
            if (item.responder == null) {
                continue;
            }
            TouchInteraction touchInteraction = item.responder.getTouchInteraction(downX, downY);
            if (touchInteraction == null) {
                continue;
            }
            return touchInteraction;
        }
        return null;
    }

    @Override
    public KeypressInteraction getKeypressInteraction() {
        return null;
    }
}
