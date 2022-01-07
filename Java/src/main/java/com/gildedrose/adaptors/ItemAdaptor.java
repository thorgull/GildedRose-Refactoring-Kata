package com.gildedrose.adaptors;

import com.gildedrose.Item;

public interface ItemAdaptor {
    String CONJURED = "Conjured Mana Cake";
    String AGED_BRIE = "Aged Brie";
    String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    String SULFURAS = "Sulfuras, Hand of Ragnaros";

    static ItemAdaptor adapt(Item item) {
        switch (item.name) {
            case CONJURED:
                return new ConjuredAdaptor(item);
            case AGED_BRIE:
                return new AgedBrieAdaptor(item);
            case BACKSTAGE_PASSES:
                return new BackstagePassesAdaptor(item);
            case SULFURAS:
                return new SulfurasAdaptor(item);
            default:
                return new DefaultAdaptor(item);
        }
    }

    Item getItem();
    void updateQuality();
    default void updateSellIn() {
        getItem().sellIn -= 1;
    }
}
