package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    
    @Test
    void itemEnFecha() {
        Item[] items = new Item[] { new Item("item", 3, 22) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(21, app.items[0].quality);
    }
    
    
    @Test
    void itemEnFechaMenos0() {
        Item[] items = new Item[] { new Item("item", 3, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }
    
    @Test
    void itemNoFecha() {
        Item[] items = new Item[] { new Item("item", -2, 22) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(20, app.items[0].quality);
    }
    
    @Test
    void itemNoFechaCalidad1() {
        Item[] items = new Item[] { new Item("item", -2, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }
    
    @Test
    void agedBrieEnFecha() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 34) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(35, app.items[0].quality);
    }
    
    @Test
    void agedBrieEnFechaLimite50() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }
    
    @Test
    void agedBrieNoFecha() {
        Item[] items = new Item[] { new Item("Aged Brie", -5, 34) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(36, app.items[0].quality);
    }
    
    @Test
    void sulfuras() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -5, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }
    
    @Test
    void backstage10() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 7, 38) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(40, app.items[0].quality);
    }
    
    @Test
    void backstage5() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 4, 38) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(41, app.items[0].quality);
    }
    
    @Test
    void backstage0() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 38) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }
    
    @Test
    void sellinMenos1() {
        Item[] items = new Item[] { new Item("item", 4, 38) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(3, app.items[0].sellIn);
    }
}
    
   