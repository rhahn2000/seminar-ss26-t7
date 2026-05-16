package Measuring;

public class Main {
    public static void main (String[] args) {
        Spoon spoons = new Spoon(4, "Water" );
        Bottle bottles = new Bottle(2, 2);
        System.out.println(spoons.getInMl() + bottles.getInMl());
        System.out.println(spoons.getInMg());
    }
}
