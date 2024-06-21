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
				agedBrieQuality(items[i]);
				break;

			case "Backstage passes to a TAFKAL80ETC concert":
				backstageQuality(items[i]);
				break;

			case "Sulfuras, Hand of Ragnaros":
				items[i].quality = 80;
				break;

			default:
				itemQuality(items[i]);
				break;
			}
		}
	}

	private void itemQuality(Item item) {
		if (item.sellIn > 0 && item.quality > 0) {
			item.quality --; 
		}
		if (item.sellIn <= 0 && item.quality > 1) {
			item.quality -= 2;
		}else if(item.sellIn <= 0 && item.quality > 0){
			item.quality --;
		}
		item.sellIn --;

	}

	private void backstageQuality(Item item) {
		if (item.sellIn > 5 && item.sellIn <= 10) {
			if (item.quality == 49) {
				item.quality ++;
			}else {
				item.quality += 2;
			}
		} else if (item.sellIn > 0 && item.sellIn <= 5) {
			if (item.quality == 49) {
				item.quality ++;
			}else if (item.quality == 48) {
				item.quality += 2;
			}else if (item.quality != 50){
				item.quality += 3;
			}
		} else if(item.sellIn == 0 ) {
			item.quality = 0;
		}
		item.sellIn --;
	}

	private void agedBrieQuality(Item item ) {
		if (item.sellIn > 0 && item.quality < 50) {
			item.quality ++;
		} else if (item.sellIn <= 0 && item.quality <= 48) {
			item.quality += 2;
		}else if (item.sellIn <= 0 && item.quality == 49)
			item.quality ++;
		item.sellIn --;
	}

	
	
}
