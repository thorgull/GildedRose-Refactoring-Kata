package com.gildedrose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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

    void genericTest(int times, TestCase... testCases) {
        Item[] items = Arrays.stream(testCases).map(TestCase::createItem).toArray(Item[]::new);
        GildedRose app = new GildedRose(items);
        IntStream.range(0, times).forEach(i -> app.updateQuality());
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
        String name = "foo";
        for (int d = -10; d <= 0; d++) {
            genericTest(
                1, new TestCase(name, d, 0, d-1, 0),
                new TestCase(name, d, 1, d-1, 0),
                new TestCase(name, d, 2, d-1, 0),
                new TestCase(name, d, 3, d-1, 1)
            );
        }
        for (int d = 1; d < 10; d++) {
            genericTest(
                1, new TestCase(name, d, 0, d-1, 0),
                new TestCase(name, d, 1, d-1, 0),
                new TestCase(name, d, 2, d-1, 1)
            );
        }
    }

    @Test
    void testThirtyDays() {
        genericTest(30,
            new TestCase("+5 Dexterity Vest", 10, 20, -20, 0),
            new TestCase("Aged Brie", 2, 0, -28, 50),
            new TestCase("Elixir of the Mongoose", 5, 7, -25, 0),
            new TestCase("Sulfuras, Hand of Ragnaros", 0, 80, 0, 80),
            new TestCase("Sulfuras, Hand of Ragnaros", -1, 80, -1, 80),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", 15, 20, -15, 0),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", 10, 49, -20, 0),
            new TestCase("Backstage passes to a TAFKAL80ETC concert", 5, 49, -25, 0),
            new TestCase("Conjured Mana Cake", 3, 6, -27, 0)
        );
    }

    @Test
    void testSulfuras() {
        String name = "Sulfuras, Hand of Ragnaros";
        for (int q = 0; q < 80; q++) {
            for (int d = -10; d <= 10; d++) {
                genericTest(1,
                    new TestCase(name, d, q, d, q)
                );
            }
        }
    }

    @Test
    void testBackstagePasses() {
        String name = "Backstage passes to a TAFKAL80ETC concert";
        for (int d = 20; d > 10; d--) {
            genericTest(1,
                new TestCase(name, d, 0, d-1, 1),
                new TestCase(name, d, 48, d-1, 49),
                new TestCase(name, d, 49, d-1, 50),
                new TestCase(name, d, 50, d-1, 50)
            );
        }
        for (int d = 10; d > 5; d--) {
            genericTest(1,
                new TestCase(name, d, 0, d-1, 2),
                new TestCase(name, d, 47, d-1, 49),
                new TestCase(name, d, 48, d-1, 50),
                new TestCase(name, d, 49, d-1, 50),
                new TestCase(name, d, 50, d-1, 50)
            );
        }
        for (int d = 5; d > 0; d--) {
            genericTest(1,
                new TestCase(name, d, 0, d-1, 3),
                new TestCase(name, d, 46, d-1, 49),
                new TestCase(name, d, 47, d-1, 50),
                new TestCase(name, d, 48, d-1, 50),
                new TestCase(name, d, 49, d-1, 50),
                new TestCase(name, d, 50, d-1, 50)
            );
        }
        for (int d = 0; d > -10; d--) {
            genericTest(1,
                new TestCase(name, d, 0, d-1, 0)
            );
        }
    }

    @Test
    void testAgedBrie() {
        String name = "Aged Brie";
        for (int d = -10; d <= 0; d++) {
            genericTest(1,
                new TestCase(name, d, 0, d-1, 2),
                new TestCase(name, d, 1, d-1, 3),
                new TestCase(name, d, 47, d-1, 49),
                new TestCase(name, d, 48, d-1, 50),
                new TestCase(name, d, 49, d-1, 50),
                new TestCase(name, d, 50, d-1, 50)
            );
        }
        for (int d = 1; d <= 20; d++) {
            genericTest(1,
                new TestCase(name, d, 0, d-1, 1),
                new TestCase(name, d, 1, d-1, 2),
                new TestCase(name, d, 47, d-1, 48),
                new TestCase(name, d, 48, d-1, 49),
                new TestCase(name, d, 49, d-1, 50),
                new TestCase(name, d, 50, d-1, 50)
            );
        }
    }

    @Test
    void testConjured() {
        String name = "Conjured Mana Cake";
        for (int d = -10; d <= 0; d++) {
            genericTest(1,
                new TestCase(name, d, 0, d-1, 0),
                new TestCase(name, d, 1, d-1, 0),
                new TestCase(name, d, 2, d-1, 0),
                new TestCase(name, d, 3, d-1, 0),
                new TestCase(name, d, 4, d-1, 0),
                new TestCase(name, d, 5, d-1, 1),
                new TestCase(name, d, 6, d-1, 2),
                new TestCase(name, d, 7, d-1, 3)
            );
        }
        for (int d = 1; d < 10; d++) {
            genericTest(1,
                new TestCase(name, d, 0, d-1, 0),
                new TestCase(name, d, 1, d-1, 0),
                new TestCase(name, d, 2, d-1, 0),
                new TestCase(name, d, 3, d-1, 1),
                new TestCase(name, d, 4, d-1, 2),
                new TestCase(name, d, 5, d-1, 3)
            );
        }
    }

}
