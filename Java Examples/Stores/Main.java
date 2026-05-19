package Stores;

public class Main {
    public static void main (String[] args) {
        System.out.println("\n\n\n------------ New Store ------------");
        BookStore bookStore = new BookStore("Fantastic Book Store", "Arthur Dent");
        bookStore.displayStoreInfo();
        System.out.println("\n\n\n------------ Adding first books ------------");
        Book book1 = new Book(
                "The Hitchhiker's Guide to the Galaxy",
                "Douglas Adams",
                "Random House LLC US",
                8.99
        );
        Book book2 = new Book(
                "Project Hail Mary",
                "Andy Weir",
                "Vermilion",
                12.59
        );
        Book book3 = new Book(
                "Ready Player One",
                "Ernest Cline",
                "Vermilion",
                9.99
        );
        book1.displayInfo();
        book2.displayInfo();
        book3.displayInfo();
        System.out.println("-----------");
        bookStore.addItemToStorage(book1);
        bookStore.addItemToStorage(book2);
        bookStore.addItemToStorage(book3);
        bookStore.displayStoreInfo();
        System.out.println("-----------");
        System.out.println("Book 1: " + bookStore.getPriceOfItem(book1.getTitle()) + "€");
        System.out.println("Book 2: " + bookStore.getPriceOfItem(book2.getTitle()) + "€");
        System.out.println("Book 3: " + bookStore.getPriceOfItem(book3.getTitle()) + "€");
        System.out.println("\n\n\n------------ Adding a discount ------------");
        bookStore.addNewAvailableDiscount(book2, 0.2);
        bookStore.displayStoreInfo();
    }
}
