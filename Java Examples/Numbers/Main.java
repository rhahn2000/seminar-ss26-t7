package Numbers;

public class Main {
    public static void main (String[] args) {
        Decimal d1 = new Decimal("12");
        ChaosNumber c1 = new ChaosNumber("1010Abc");
        System.out.println(d1.subtract(c1)); // will throw an error as both numbers do not have the same type
    }
}
