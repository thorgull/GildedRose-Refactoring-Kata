package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] { new Item("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals("foo", -1, 0, app.items[0]);
    }

    static void assertItemEquals(String name, int sellIn, int quality, Item item) {
        assertEquals(name, item.name);
        assertEquals(sellIn, item.sellIn);
        assertEquals(quality, item.quality);
    }

    static String toString(Item item) {
        return "Item { name: " + item.name + ", sellIn: " + item.sellIn + ", quality: "+ item.quality + "}";
    }
}
