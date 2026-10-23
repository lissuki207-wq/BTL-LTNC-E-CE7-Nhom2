package model;

import java.util.ArrayList;
import java.util.List;

public class Seller extends User {
    private List<Item> itemsForSale; // list an item for sale:List

    public Seller(int id, String name, short age, double balance, String email, String password) {
        super(id, name, age, User.ROLE_SELLER, balance, email, password);
        this.itemsForSale = new ArrayList<>();
    }

    @Override
    public String getRoleDescription() {
        return "Seller (Người bán)";
    }

    // list an item for sale:List
    public List<Item> listItemForSale(Item item) {
        if (getCurrentStatus() != STATUS_BANNED && item != null) {
            itemsForSale.add(item);
        }
        return itemsForSale;
    }

    // set desired price: double
    public double setDesiredPrice(Item item, double price) {
        if (item != null && itemsForSale.contains(item) && price >= 0) {
            item.setDesiredPrice(price);
            return item.getDesiredPrice();
        }
        return -1;
    }

    public List<Item> getItemsForSale() {
        return itemsForSale;
    }
}
