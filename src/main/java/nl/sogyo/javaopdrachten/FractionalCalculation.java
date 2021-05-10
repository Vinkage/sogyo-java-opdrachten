package nl.sogyo.javaopdrachten;

public class FractionalCalculation {

    public static void main(String[] args) {
        FractionalCalculation fracDrive = new FractionalCalculation();
        fracDrive.toDecimalTestDrive();
        fracDrive.toStringTestDrive();
        fracDrive.addTestDrive();
        fracDrive.subtractTestDrive();
        fracDrive.multiplyTestDrive();
        fracDrive.divideTestDrive();
    }

    private void divideTestDrive() {
        System.out.println("Testing division operations");
        Fraction fraction1 = new Fraction(3, 2);
        Fraction fraction2 = new Fraction(3, 10);
        Fraction fraction3 = new Fraction(2, 5);
        fraction1.divide(2);
        System.out.println("3/2 / 2 = " + fraction1.toString());
        fraction2.divide(fraction3);
        System.out.println("3/10 / 2/5 = " + fraction2.toString());
        System.out.println();
    }


    private void multiplyTestDrive() {
        System.out.println("Testing multiplication operations");
        Fraction fraction1 = new Fraction(3, 4);
        Fraction fraction2 = new Fraction(3, 4);
        Fraction fraction3 = new Fraction(2, 5);
        fraction1.multipy(2);
        System.out.println("3/4 * 2 = " + fraction1.toString());
        fraction2.multipy(fraction3);
        System.out.println("3/4 * 2/5 = " + fraction2.toString());
        System.out.println();
    }

    private void subtractTestDrive() {
        System.out.println("Testing subtraction operations");
        Fraction fraction1 = new Fraction(4, 3);
        Fraction fraction2 = new Fraction(1, 2);
        Fraction fraction3 = new Fraction(1, 6);
        fraction1.subtract(1);
        System.out.println("4/3 - 1 = " + fraction1.toString());
        fraction2.subtract(fraction3);
        System.out.println("1/2 - 1/6 = " + fraction2.toString());
        System.out.println();
    }

    private void addTestDrive() {
        System.out.println("Testing adding operations");
        Fraction fraction1 = new Fraction(1, 3);
        fraction1.add(1);
        System.out.println("1/3 + 1 = " + fraction1.toString());

        Fraction fraction2 = new Fraction(1, 3);
        Fraction fractionOneSixth = new Fraction(1, 6);

        fraction2.add(fractionOneSixth);
        System.out.println("1/3 + 1/6 = " + fraction2.toString());

        Fraction fraction3 = new Fraction(3, 7);
        Fraction fractionOneHalf = new Fraction(112, 113);
        fraction3.add(fractionOneHalf);
        System.out.println("3/7 + 112/113 = " + fraction3.toString());
        System.out.println();
    }

    private void toStringTestDrive() {
        System.out.println("toString test");
        Fraction fraction = new Fraction(1, 3);
        System.out.println(fraction.toString());
        System.out.println();
    }

    private void toDecimalTestDrive() {
        System.out.println("toDecimal test");
        Fraction fraction = new Fraction(1, 3);
        System.out.println(fraction.toDecimalNotation());
        System.out.println();
    }
}

