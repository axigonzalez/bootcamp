package com.gildedrose;

public class FuncionesAritmeticas {


	public static void resta1(Item item) {
		if (item.quality > 0) {
			item.quality --;
		} else {
			item.quality = 0;
		}
	}

	public static void resta2(Item item) {
		if (item.quality == 1) {
			resta1(item);
		} else if (item.quality > 1) {
			item.quality -= 2;
		}
	}

	public static void suma1(Item item) {
		if (item.quality < 50) {
			item.quality++;
		}
	}

	public static void suma2(Item item) {
		if (item.quality < 48) {
			item.quality += 2;
		} else if (item.quality == 49) {
			suma1(item);
		}
	}

	public static void suma3(Item item) {
		if (item.quality == 48) {
			suma2(item);
		} else if (item.quality == 49) {
			suma1(item);
		} else {
			item.quality += 3;
		}
	}
	
	public static void resta1Selling(Item item) {
		item.sellIn --;
	}
}
