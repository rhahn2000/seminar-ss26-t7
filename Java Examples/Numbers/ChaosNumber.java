package Numbers;

public class ChaosNumber implements Numbers {
    String value;

    ChaosNumber (String value) {
        this.value = value;
    }

    @Override
    public Numbers subtract(Numbers subtrahend) {
        ChaosNumber sub = (ChaosNumber) subtrahend;
        int diff = Integer.parseInt(this.value) - Integer.parseInt(sub.value);
        return new ChaosNumber(Integer.toString(diff));
    }
}