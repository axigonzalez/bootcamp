package com.gildedrose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

class GildedRoseTest {

    
    @Test
    @DisplayName("Item no caducado")
    void itemEnFecha() {
        Item[] items = new Item[] { new Item("item", 3, 22) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(21, app.items[0].quality);
    }
    
    
    @Test
    @DisplayName("Item no caducado y calidad 0")
    void itemEnFechaMenos0() {
        Item[] items = new Item[] { new Item("item", 3, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }
    
    @Test
    @DisplayName("Item caducado")
    void itemNoFecha() {
        Item[] items = new Item[] { new Item("item", -2, 22) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(20, app.items[0].quality);
    }
    
    @Test
    @DisplayName("Item caducado y calidad 1")
    void itemNoFechaCalidad1() {
        Item[] items = new Item[] { new Item("item", -2, 1) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }
    
    @Test
    @DisplayName("AgedBrie no caducado")
    void agedBrieEnFecha() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 34) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(35, app.items[0].quality);
    }
    
    @Test
    @DisplayName("AgedBrie no caducado y calidad max.")
    void agedBrieEnFechaLimite50() {
        Item[] items = new Item[] { new Item("Aged Brie", 5, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(50, app.items[0].quality);
    }
    
    @Test
    @DisplayName("AgedBrie caducado")
    void agedBrieNoFecha() {
        Item[] items = new Item[] { new Item("Aged Brie", -5, 34) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(36, app.items[0].quality);
    }
    
    @Test
    @DisplayName("Sulfuras")
    void sulfuras() {
        Item[] items = new Item[] { new Item("Sulfuras, Hand of Ragnaros", -5, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(80, app.items[0].quality);
    }
    
    @Test
    @DisplayName("Backstage 10")
    void backstage10() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 7, 38) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(40, app.items[0].quality);
    }
    
    @Test
    @DisplayName("Backstage 5")
    void backstage5() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 4, 38) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(41, app.items[0].quality);
    }
    
    @Test
    @DisplayName("Backstage 0")
    void backstage0() {
        Item[] items = new Item[] { new Item("Backstage passes to a TAFKAL80ETC concert", 0, 38) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items[0].quality);
    }
    
    @Test
    @DisplayName("SellIn -1")
    void sellinMenos1() {
        Item[] items = new Item[] { new Item("item", 4, 38) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(3, app.items[0].sellIn);
    }
    
    @ParameterizedTest(name = "Caso {index}: Nombre: {0}  SellIn: {1}  Quality: {2}")
	@DisplayName("Prueba de productos")
	@CsvSource(value = {"Item,5,23,22",
			"Item,-2,19,17",
			"Aged Brie,5,19,20",
			"Aged Brie,-2,19,21", 
			"Aged Brie,-3,49,50",
			"Backstage passes to a TAFKAL80ETC concert, 7, 38,40",
			"Backstage passes to a TAFKAL80ETC concert, 7, 49,50",
			"Backstage passes to a TAFKAL80ETC concert, 5, 47,50",
			"Backstage passes to a TAFKAL80ETC concert, 5, 48,50",
			"Backstage passes to a TAFKAL80ETC concert, 5, 49,50",
			"Backstage passes to a TAFKAL80ETC concert,0, 38,0"})
	void testAdd(String name, int sellin, int quality, int result) {
    	Item[] items = new Item[] { new Item(name, sellin, quality) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(result, app.items[0].quality);
	}
}
    
   