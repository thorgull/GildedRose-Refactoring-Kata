package com.gildedrose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        Item[] items = new Item[] {
            new Item("foo", 0, 0),
            new Item("foo", 1, 1),
            new Item("foo", 10, 20),
            new Item("foo", -1, 0),
            new Item("foo", -1, 5),
        };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertItemEquals("foo", -1, 0, app.items[0]);
        assertItemEquals("foo", 0, 0, app.items[1]);
        assertItemEquals("foo", 9, 19, app.items[2]);
        assertItemEquals("foo", -2, 0, app.items[3]);
        assertItemEquals("foo", -2, 3, app.items[4]);
    }

    static void assertItemEquals(String name, int sellIn, int quality, Item item) {
        Assertions.assertAll(
            () -> assertEquals(name, item.name, "invalid name"),
            () -> assertEquals(sellIn, item.sellIn, "invalid sellIn"),
            () -> assertEquals(quality, item.quality, "invalid quantity")
        );
    }

    static String toString(Item item) {
        return "Item { name: " + item.name + ", sellIn: " + item.sellIn + ", quality: "+ item.quality + "}";
    }
}
