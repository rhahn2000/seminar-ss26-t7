package Stores;

public interface NonFoodStore {
    void showAvailableDiscount();
    void addNewAvailableDiscount(StoreItem storeItem, double discount);
    void removeAvailableDiscount(StoreItem storeItem);
}
