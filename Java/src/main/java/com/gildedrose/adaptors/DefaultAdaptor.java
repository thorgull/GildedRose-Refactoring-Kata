package com.gildedrose.adaptors;

import com.gildedrose.Item;

public class DefaultAdaptor implements ItemAdaptor {
    private final Item item;

    public DefaultAdaptor(Item item) {
        this.item = item;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void updateQuality() {
        if (item.sellIn <= 0) {
            item.quality = Math.max(0, item.quality - 2);
        } else {
            item.quality = Math.max(0, item.quality - 1);
        }
    }
}
