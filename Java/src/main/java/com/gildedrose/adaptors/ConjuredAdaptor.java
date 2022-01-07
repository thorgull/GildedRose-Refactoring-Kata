package com.gildedrose.adaptors;

import com.gildedrose.Item;

public class ConjuredAdaptor implements ItemAdaptor {
    private final Item item;

    public ConjuredAdaptor(Item item) {
        this.item = item;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void updateQuality() {
        if (item.sellIn <= 0) {
            item.quality = Math.max(0, item.quality - 4);
        } else {
            item.quality = Math.max(0, item.quality - 2);
        }
    }
}
