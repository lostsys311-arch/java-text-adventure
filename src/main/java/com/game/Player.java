package com.game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Item> items;

    public Player() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                items.remove(item);
                return item;
            }
        }
        return null;
    }

    public List<Item> getItems() {
        return items;
    }
}
