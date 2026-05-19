package Stores;

import java.util.ArrayList;

public class BookStore implements Store, NonFoodStore {
    private ArrayList<Discount> discounts = new ArrayList<>();
    private ArrayList<Book> books = new ArrayList<>();
    private String storeName;
    private String owner;

    BookStore(String storeName, String owner) {
        this.storeName = storeName;
        this.owner = owner;
    }

    @Override
    public void showAvailableDiscount() {
        for (Discount discount : discounts) {
            Book book = (Book) discount.getStoreItem();
            String normalPrice = String.format("%.2f€", book.getPrice());
            String discountedPrice = String.format("%.2f€", discount.calculateDiscount());
            System.out.println(book.getTitleAndAuthor() + ": " + normalPrice + " " + discount.displayDiscount() + " = " + discountedPrice);
        }
    }

    @Override
    public void addNewAvailableDiscount(StoreItem storeItem, double discount) {
        Discount discountItem = new Discount();
        discountItem.setStoreItem(storeItem);
        discountItem.setDiscount(discount);
        discounts.add(discountItem);
    }

    @Override
    public void removeAvailableDiscount(StoreItem storeItem) {
        discounts.forEach(discount -> {
            if (discount.getStoreItem().equals(storeItem)) {
                discounts.remove(discount);
            }
        });
    }

    @Override
    public double getPriceOfItem(String itemName) {
        for(Book book : books) {
            if(book.getTitle().equals(itemName)) {
                return book.getPrice();
            }
        }
        throw new IllegalArgumentException("Book not found: " + itemName);
    }

    @Override
    public void displayStoreInfo() {
        System.out.println("Store Name: " + storeName);
        System.out.println("Owner Name: " + owner);
        System.out.println("-----------");
        if(!discounts.isEmpty()) {
            System.out.println("Available discounts:");
            showAvailableDiscount();
        } else {
            System.out.println("No discounts available");
        }
        System.out.println("-----------");
        if(!books.isEmpty()) {
            System.out.println("Current stock:");
            displayStorage();
        } else {
            System.out.println("No books available");
        }
    }

    @Override
    public void displayStorage() {
        for (Book book : books) {
            book.displayInfo();
        }
    }

    @Override
    public void addItemToStorage(StoreItem storeItem) {
        Book book = (Book) storeItem;
        books.add(book);
    }
}
