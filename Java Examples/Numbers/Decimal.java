package Numbers;

public class Decimal implements Numbers {
    String value;

    Decimal (String value) {
        this.value = value;
    }

    @Override
    public Numbers subtract(Numbers subtrahend) {
        Decimal sub = (Decimal) subtrahend;
        int diff = Integer.parseInt(this.value) - Integer.parseInt(sub.value);
        return new ChaosNumber(Integer.toString(diff));
    }
}
