package hu.schonherz.y2014.partyappandroid.util.datamodell;

public class MenuItem {
    public int id;
    public String name;
    public int price;
    public String currency;
    public String unit;
    public int discount;
    public String menu_category;
    public int menu_sort;

    public MenuItem(int id, String name, int price, String currency, String unit, int discount, String menu_category,
            int menu_sort) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.currency = currency;
        this.unit = unit;
        this.discount = discount;
        this.menu_category = menu_category;
        this.menu_sort = menu_sort;
    }

    public int getPositionCurrencySpinner() {
        if (this.currency.equals("HUF"))
            return 0;
        if (this.currency.equals("EUR"))
            return 1;
        if (this.currency.equals("USD"))
            return 2;
        return 0;
    }
}
