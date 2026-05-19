package Stores;

public interface Store {
    double getPriceOfItem(String itemName);
    void displayStoreInfo();
    void displayStorage();
    void addItemToStorage(StoreItem storeItem);
}
