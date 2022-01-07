package com.gildedrose;

import com.gildedrose.adaptors.ItemAdaptor;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemAdaptor adaptor = ItemAdaptor.adapt(item);
            adaptor.updateQuality();
            adaptor.updateSellIn();
        }
    }
}
