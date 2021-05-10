package nl.sogyo.javaopdrachten;

public class Fraction {
    private int numer, denom;

    public Fraction(int numer, int denom) {
        this.numer = numer;
        this.denom = denom;
    }

    public double toDecimalNotation() {
        return (this.numer) / (double)(this.denom);
    }

    public String toString() {
        return Integer.toString(this.numer) + "/" + Integer.toString(this.denom);
    }

    public int getNumer() {
        return numer;
    }

    public int getDenom() {
        return denom;
    }

    public void add(int term) {
        this.numer = this.numer + (term * this.denom);
        reduce();
    }

    public void add(Fraction term) {
        commonDenomForm(this, term);
        this.numer = this.numer + term.numer;
        reduce();
    }

    public void subtract(int term) {
        this.numer = this.numer - (term * this.denom);
        reduce();
    }

    public void subtract(Fraction term) {
        commonDenomForm(this, term);
        this.numer = this.numer - term.numer;
        reduce();
    }

    public void multipy(int factor) {
        this.numer = this.numer * factor;
        reduce();
    }

    public void multipy(Fraction factor) {
        this.numer = this.numer * factor.numer;
        this.denom = this.denom * factor.denom;
        reduce();
    }

    public void divide(int factor) {
        this.denom = this.denom * factor;
        reduce();
    }

    public void divide(Fraction factor) {
        this.numer = this.numer * factor.denom;
        this.denom = this.denom * factor.numer;
        reduce();
    }


    private void commonDenomForm(Fraction a, Fraction b) {
        // Calculates lowest common multiple recursively
        int currentLCM = lcm(a.denom, b.denom);

        // Scales numerator
        if (a.denom < currentLCM) {
            a.numer = (a.numer * (currentLCM / a.denom));
        }
        if (b.denom < currentLCM) {
            b.numer = (b.numer * (currentLCM / b.denom));
        }

        // Sets new denominator
        a.denom = currentLCM;
        b.denom = currentLCM;
    }

    private void reduce() {
        // Calculates greatest common divisor recursively
        int currentGCD = gcd(this.numer, this.denom);
        // Scales the fraction
        this.numer = this.numer / currentGCD;
        this.denom = this.denom / currentGCD;
    }

    private int gcd(int a, int b) {
        // Recursive gcd
        if (b != 0) {
            return gcd(b, a % b);
        } else {
            return a;
        }
    }

    private int lcm(int a, int b) {
        // Helper to keep track of initial a and b
        return lcmHelper(a, b, a, b);
    }

    private int lcmHelper(int a, int b, int aInitial, int bInitial) {
        // Recursive lcm
        if (a == 0 || b == 0) {
            return 0;
        } else if (a > b && a % b != 0) {
            return lcmHelper(a + aInitial, b, aInitial, bInitial);
        } else if (b > a && b % a != 0) {
            return lcmHelper(a, b + bInitial, aInitial, bInitial);
        } else if (b >= a) {
            return b;
        } else {
            return a;
        }
    }
}

