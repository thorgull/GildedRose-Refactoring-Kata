package com.gildedrose.adaptors;

import com.gildedrose.Item;

public class AgedBrieAdaptor implements ItemAdaptor {
    private final Item item;

    public AgedBrieAdaptor(Item item) {
        this.item = item;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void updateQuality() {
        if (item.sellIn <= 0) {
            item.quality = Math.min(50, item.quality + 2);
        } else {
            item.quality = Math.min(50, item.quality + 1);
        }
    }

}
