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
    }

    public void divide(Fraction factor) {
        this.numer = this.numer * factor.denom;
        this.denom = this.denom * factor.numer;
        reduce();
    }


    private void commonDenomForm(Fraction a, Fraction b) {
        int currentLCM = lcm(a.denom, b.denom);
        // System.out.println("current lcm: " + currentLCM);

        if (a.denom < currentLCM) {
            a.numer = (a.numer * (currentLCM / a.denom));
        }

        if (b.denom < currentLCM) {
            b.numer = (b.numer * (currentLCM / b.denom));
        }
        a.denom = currentLCM;
        b.denom = currentLCM;
    }

    private void reduce() {
        int currentGCD = gcd(this.numer, this.denom);
        this.numer = this.numer / currentGCD;
        this.denom = this.denom / currentGCD;
    }

    public int gcd(int a, int b) {
        if (b != 0) {
            return gcd(b, a % b);
        } else {
            return a;
        }
    }

    public int lcm(int a, int b) {
        return lcmHelper(a, b, a, b);
    }

    public int lcmHelper(int a, int b, int aInitial, int bInitial) {
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

    public int getNumer() {
        return numer;
    }

    public int getDenom() {
        return denom;
    }

}

