package Measuring;

import static java.lang.Math.round;

public class Spoon implements Measuring, MeasuringExt {
    private int amount = 0;
    private float density = 0f;

    public Spoon(int amount, String ingredient) {
        this.amount = amount;
        switch (ingredient) {
            case "Water":
                this.density = 1.0f;
                break;
            case "Sugar":
                this.density = 0.845f;
                break;
            default:
                break;
        }
    }

    public int getInMl() {
        return this.amount * 15;
    }

    public int getInMg() {
        return amount * 15 * round(density * 1000);
    }

}
