package Measuring;

public class Bottle implements Measuring {
    private int amount = 0;
    private int litrePerBottle = 1;

    public Bottle(int amount, int litrePerBottle) {
        this.amount = amount;
        this.litrePerBottle = litrePerBottle;
    }

    public int getInMl() {
        return this.amount * this.litrePerBottle * 1000;
    }

}
