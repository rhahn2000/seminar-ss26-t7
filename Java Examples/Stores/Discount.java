package Stores;

import static java.lang.Math.round;

public class Discount {
    private double discount;
    private StoreItem storeItem;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public StoreItem getStoreItem() {
        return storeItem;
    }

    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
    }

    public String displayDiscount() {
        return "-" + (discount * 100) + "%";
    }

    public double calculateDiscount() {
        return round(storeItem.getPrice() - (storeItem.getPrice() * discount));
    }
}
