package com.gildedrose;


class GildedRose {
	Item[] items;

	public GildedRose(Item[] items) {
		this.items = items;
	}

	public void updateQuality() {
		for (int i = 0; i < items.length; i++) {

			switch (items[i].name) {

			case "Aged Brie":

				if (items[i].sellIn > 0) {
					FuncionesAritmeticas.suma1(items[i]);
				} else if (items[i].sellIn <= 0) {
					FuncionesAritmeticas.suma2(items[i]);
				}
				FuncionesAritmeticas.resta1Selling(items[i]);
				break;

			case "Backstage passes to a TAFKAL80ETC concert":

				if (items[i].sellIn > 5 && items[i].sellIn <= 10) {
					FuncionesAritmeticas.suma2(items[i]);
				} else if (items[i].sellIn > 0 && items[i].sellIn <= 5) {
					FuncionesAritmeticas.suma3(items[i]);
				} else {
					items[i].quality = 0;
				}
				FuncionesAritmeticas.resta1Selling(items[i]);
				break;

			case "Sulfuras, Hand of Ragnaros":
				items[i].quality = 80;
				break;

			default:
				if (items[i].sellIn > 0) {
					FuncionesAritmeticas.resta1(items[i]);
				} else if (items[i].sellIn <= 0) {
					FuncionesAritmeticas.resta2(items[i]);
				}
				FuncionesAritmeticas.resta1Selling(items[i]);
				break;
			}
		}
	}

	
}
