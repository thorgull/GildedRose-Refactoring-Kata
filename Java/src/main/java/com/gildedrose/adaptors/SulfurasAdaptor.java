package com.gildedrose.adaptors;

import com.gildedrose.Item;

public class SulfurasAdaptor implements ItemAdaptor {
    private final Item item;

    public SulfurasAdaptor(Item item) {
       this.item = item;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public void updateQuality() {
        // Quality never change
    }

    @Override
    public void updateSellIn() {
        // SellIn never change
    }
}
