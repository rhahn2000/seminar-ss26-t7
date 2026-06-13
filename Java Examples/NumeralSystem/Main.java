package NumeralSystem;

public class Main {
    public static void main (String[] args) {
        Binary b1 = new Binary("this is a binary system (base equals 2)");
        System.out.println(b1.convertNumber(42));
        b1.displaySystem();
    }
}
