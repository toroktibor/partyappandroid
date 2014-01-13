package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class MenuItem {
	int id;
	String name;
	int price;
	String currency;
	String unit;
	int discount;
	String menu_category;
	int menu_sort;
	
	public MenuItem(int id, String name, int price, String currency, String unit, int discount, String menu_category, int menu_sort) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.currency = currency;
		this.unit = unit;
		this.discount = discount;
		this.menu_category = menu_category;
		this.menu_sort = menu_sort;
	}
}