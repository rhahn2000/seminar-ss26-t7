package NumeralSystem;

public class Binary implements NumeralSystem {
    String details;

    Binary (String details) {
        this.details = details;
    }

    @Override
    public String convertNumber (int number) {
        return Integer.toBinaryString(number);
    }

    @Override
    public void displaySystem () {
        System.out.println("System Name: Binary");
        System.out.println("Details: " + this.details);
    }
}
