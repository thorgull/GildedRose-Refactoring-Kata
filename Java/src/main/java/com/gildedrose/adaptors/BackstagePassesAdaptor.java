package com.gildedrose.adaptors;

import com.gildedrose.Item;

public class BackstagePassesAdaptor implements ItemAdaptor {
    private final Item item;

    public BackstagePassesAdaptor(Item item) {
        this.item = item;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void updateQuality() {
        if (item.sellIn <= 0) {
            item.quality = 0;
        } else if (item.sellIn <= 5) {
            item.quality = Math.min(50, item.quality + 3);
        } else if (item.sellIn <= 10) {
            item.quality = Math.min(50, item.quality + 2);
        } else {
            item.quality = Math.min(50, item.quality + 1);
        }
    }

}
