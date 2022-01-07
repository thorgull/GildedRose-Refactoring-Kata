package com.gildedrose;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.List;
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
    void foo() {
        genericTest(
            new TestCase("foo", 0, 0, -1, 0),
            new TestCase("foo", 1, 1, 0, 0),
            new TestCase("foo", 10, 20, 9, 19),
            new TestCase("foo", -1, 0, -2, 0),
            new TestCase("foo", -1, 1, -2, 0),
            new TestCase("foo", -1, 5, -2, 3)
        );
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


    static String toString(Item item) {
        return "Item { name: " + item.name + ", sellIn: " + item.sellIn + ", quality: "+ item.quality + "}";
    }
}
