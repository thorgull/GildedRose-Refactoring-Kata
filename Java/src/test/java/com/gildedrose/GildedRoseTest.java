package com.gildedrose;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    static class TestCase {
        final String name;
        final int initialSellIn;
        final int initialQuality;
        final int expectedSellIn;
        final int expectedQuality;

        public TestCase(String name, int initialSellIn, int initialQuality, int expectedSellIn, int expectedQuality) {
            this.name = name;
            this.initialSellIn = initialSellIn;
            this.initialQuality = initialQuality;
            this.expectedSellIn = expectedSellIn;
            this.expectedQuality = expectedQuality;
        }

        public Item createItem() {
            return new Item(name, initialSellIn, initialQuality);
        }

        public void assertCorrect(Item item) {
            assertAll(
                "Failed test case : " + this,
                () -> assertEquals(name, item.name, "invalid name"),
                () -> assertEquals(expectedSellIn, item.sellIn, "invalid sellIn"),
                () -> assertEquals(expectedQuality, item.quality, "invalid quantity")
            );
        }

        @Override
        public String toString() {
            return "TestCase{" +
                "name='" + name + '\'' +
                ", initialSellIn=" + initialSellIn +
                ", initialQuality=" + initialQuality +
                ", expectedSellIn=" + expectedSellIn +
                ", expectedQuality=" + expectedQuality +
                '}';
        }
    }

    void genericTest(TestCase... testCases) {
        Item[] items = Arrays.stream(testCases).map(TestCase::createItem).toArray(Item[]::new);
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(testCases.length, app.items.length);

        Assertions.assertAll(
            IntStream.range(0, testCases.length)
                .<org.junit.jupiter.api.function.Executable>mapToObj((index) ->
                    () -> testCases[index].assertCorrect(app.items[index])
                ).toArray(Executable[]::new)
        );
    }

    @Test
    void testDefaults() {
        for (int d = -10; d <= 0; d++) {
            genericTest(
                new TestCase("foo", d, 0, d-1, 0),
                new TestCase("foo", d, 1, d-1, 0),
                new TestCase("foo", d, 2, d-1, 0),
                new TestCase("foo", d, 3, d-1, 1)
            );
        }
        for (int d = 1; d < 10; d++) {
            genericTest(
                new TestCase("foo", d, 0, d-1, 0),
                new TestCase("foo", d, 1, d-1, 0),
                new TestCase("foo", d, 2, d-1, 1)
            );
        }
    }

    @Test
    void testSulfuras() {
        for (int q = 0; q < 80; q++) {
            for (int d = -10; d <= 10; d++)
            genericTest(
                new TestCase("Sulfuras, Hand of Ragnaros", d, q, d, q)
            );
        }
    }

    @Test
    void testBackstagePasses() {
        for (int d = 20; d > 10; d--) {
            genericTest(
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 0, d-1, 1),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 48, d-1, 49),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 49, d-1, 50),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 50, d-1, 50)
            );
        }
        for (int d = 10; d > 5; d--) {
            genericTest(
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 0, d-1, 2),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 47, d-1, 49),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 48, d-1, 50),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 49, d-1, 50),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 50, d-1, 50)
            );
        }
        for (int d = 5; d > 0; d--) {
            genericTest(
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 0, d-1, 3),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 46, d-1, 49),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 47, d-1, 50),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 48, d-1, 50),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 49, d-1, 50),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", d, 50, d-1, 50)
            );
        }
        genericTest(
            new TestCase("Backstage passes to a TAFKAL80ETC concert", 0, 0, -1, 0)
        );
    }

    @Test
    void testAgedBrie() {
        for (int d = -10; d <= 0; d++) {
            genericTest(
                new TestCase("Aged Brie", d, 0, d-1, 2),
                new TestCase("Aged Brie", d, 1, d-1, 3),
                new TestCase("Aged Brie", d, 47, d-1, 49),
                new TestCase("Aged Brie", d, 48, d-1, 50),
                new TestCase("Aged Brie", d, 49, d-1, 50),
                new TestCase("Aged Brie", d, 50, d-1, 50)
            );
        }
        for (int d = 1; d <= 20; d++) {
            genericTest(
                new TestCase("Aged Brie", d, 0, d-1, 1),
                new TestCase("Aged Brie", d, 1, d-1, 2),
                new TestCase("Aged Brie", d, 47, d-1, 48),
                new TestCase("Aged Brie", d, 48, d-1, 49),
                new TestCase("Aged Brie", d, 49, d-1, 50),
                new TestCase("Aged Brie", d, 50, d-1, 50)
            );
        }
    }

    @Test
    void testConjured() {
        for (int d = -10; d <= 0; d++) {
            genericTest(
                new TestCase("Conjured Mana Cake", d, 0, d-1, 0),
                new TestCase("Conjured Mana Cake", d, 1, d-1, 0),
                new TestCase("Conjured Mana Cake", d, 2, d-1, 0),
                new TestCase("Conjured Mana Cake", d, 3, d-1, 0),
                new TestCase("Conjured Mana Cake", d, 4, d-1, 0),
                new TestCase("Conjured Mana Cake", d, 5, d-1, 1),
                new TestCase("Conjured Mana Cake", d, 6, d-1, 2),
                new TestCase("Conjured Mana Cake", d, 7, d-1, 3)
            );
        }
        for (int d = 1; d < 10; d++) {
            genericTest(
                new TestCase("Conjured Mana Cake", d, 0, d-1, 0),
                new TestCase("Conjured Mana Cake", d, 1, d-1, 0),
                new TestCase("Conjured Mana Cake", d, 2, d-1, 0),
                new TestCase("Conjured Mana Cake", d, 3, d-1, 1),
                new TestCase("Conjured Mana Cake", d, 4, d-1, 2),
                new TestCase("Conjured Mana Cake", d, 5, d-1, 3)
            );
        }
    }

    static String toString(Item item) {
        return "Item { name: " + item.name + ", sellIn: " + item.sellIn + ", quality: "+ item.quality + "}";
    }
}
