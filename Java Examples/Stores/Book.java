package Stores;

public class Book implements StoreItem {
    private String title;
    private String author;
    private String publisher;
    private double price;

    Book(String title, String author, String publisher, double price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
    }

    @Override
    public void displayInfo() {
        System.out.println("Title: " + title + "   Author: " + author + "   Publisher: " + publisher + "   Price: " + price + "€");
    }

    @Override
    public double getPrice() {
        return price;
    }

    public String getTitleAndAuthor() {
        return title + " " + author;
    }

    public String getTitle() {
        return title;
    }
}
