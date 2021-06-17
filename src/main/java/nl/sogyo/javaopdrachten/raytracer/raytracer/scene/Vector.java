package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

public class Vector {
    private Float x, y, z;
    private Float modulus;
    private Float theta, phi;
    private static final double EPSILON = 1e-6;

    public Vector(Vector toCopy) {
        this.x = toCopy.x;
        this.y = toCopy.y;
        this.z = toCopy.z;
        setPolarCoordinates();
    }

    public Vector(Float x, Float y, Float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        setPolarCoordinates();
    }

    public Vector(double x, double y, double z) {
        this.x = (float)x;
        this.y = (float)y;
        this.z = (float)z;
        setPolarCoordinates();
    }

    public Vector(int x, int y, int z) {
        this.x = (float)x;
        this.y = (float)y;
        this.z = (float)z;
        setPolarCoordinates();
    }

    public Vector(Float[] cartesianCoordinateArray) {
        this.x = cartesianCoordinateArray[0];
        this.y = cartesianCoordinateArray[1];
        this.z = cartesianCoordinateArray[2];
        setPolarCoordinates();
    }

    public Vector() {
    }

    public static Vector fromPolar(Float modulus, Float theta, Float phi) {
        Vector vector = new Vector();
        vector.modulus = modulus;
        vector.theta = theta;
        vector.phi = phi;
        vector.setCartesianCoordinates();
        return vector;
    }

    private void setPolarCoordinates() {
        setModulus();
        setDefinedTheta();
        setDefinedPhi();
        // System.out.println("r: " + this.modulus + ", theta: " + this.theta + ", phi: " + this.phi);
    }

    private void setCartesianCoordinates() {
        this.x = (float) (this.modulus * Math.sin(this.phi) * Math.cos(this.theta));
        // if (y != 0)
        //     this.y = (this.y / Math.abs(this.y)) * (float) (this.modulus * Math.sin(this.phi) * Math.sin(this.theta));
        // else
        //     this.y =  (float) (this.modulus * Math.sin(this.phi) * Math.sin(this.theta));
        this.y =  (float) (this.modulus * Math.sin(this.phi) * Math.sin(this.theta));
        this.z = (float) (this.modulus * Math.cos(this.phi));
    }

    Vector removeRoundOffErrorBluntly() {
        this.x = approximates(this.x);
        this.y = approximates(this.y);
        this.z = approximates(this.z);
        setPolarCoordinates();
        return this;
    }

    private float approximates(float number) {
        if (Math.abs(number - Math.round(number)) < EPSILON)
            return Math.round(number);
        return number;
    }

    public String toString() {
        return ("(" + this.x + ", " + this.y + ", " + this.z + ")");
    }

    public String toStringWithPolar() {
        return ("(" + this.x + ", " + this.y + ", " + this.z + ") mod: " + this.modulus + ", theta: " + this.theta + ", phi: " + this.phi);
    }


    public String toStringCartxyz() {
        return ("(" + x + ", " + y + ", " + z + ")");
    }

    private void setModulus() {
        this.modulus = (float) Math.sqrt(x * x + y * y + z * z);
    }

    public Float getModulus() {
        return this.modulus;
    }

    private void setDefinedPhi() {
        if (modulus == 0f) {
            this.phi = 0f;
        } else {
            this.phi = (float) Math.acos(z / modulus);
        }

    }

    private void setDefinedTheta() {
        if (x == 0 || y == 0) {
            this.theta = 0f;// (float) Math.PI /2;
        } else if (x < 0) {
            this.theta = ((float) Math.atan(y / x)) + (float) Math.PI;
        } else {
            this.theta = (float) Math.atan(y / x);
        }
    }

    public void shiftModulus(Float scalar) {
        this.modulus = this.modulus + scalar;
        this.setCartesianCoordinates();
    }

    public Vector shiftModulusAndReturn(Float scalar) {
        Vector vector;
        vector = Vector.fromPolar((this.modulus + scalar), this.theta, this.phi);
        return vector;
    }

    public void scaleModulus(Float scalar) {
        float absScalar = Math.abs(scalar);
        if (scalar < 0)
            scalarMultiplyInPlace(-1f);

        this.modulus = absScalar * this.modulus;
        this.setCartesianCoordinates();
    }

    public void scalarMultiplyInPlace(Float scalar) {
        x = x * scalar;
        y = y * scalar;
        z = z * scalar;
        setPolarCoordinates();
    }

    public Vector scalarMultiply(Float scalar) {
        Float[] newCartesian = new Float[3];
        Float[] thisCartesian = this.getCartesianCoordinates();
        for (int i = 0; i <3; i++) {
            newCartesian[i] = scalar * thisCartesian[i];
        }
        return new Vector(newCartesian);
    }

    public Float[] getCartesianCoordinates() {
        return new Float[]{this.x, this.y, this.z};
    }

    public float[] getCartesianPrimitives() {
        return new float[]{this.x, this.y, this.z};
    }


    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getZ() {
        return z;
    }

    public float getPhi() {return phi;}
    public float getTheta() {return theta;}


    public Float[] getPolarCoordinates() {
        return new Float[]{this.modulus, this.theta, this.phi};
    }


    public void rotationInPlace(Float thetaShift, Float phiShift) {
        rotationInPlace(thetaShift, phiShift, this.toUnitLengthAndReturn().crossProduct(new Vector(0, 0, 1)));
    }
    public void rotationInPlace(Float thetaShift, Float phiShift, Vector axisOfRotation) {
        Vector copy;

        if (phiShift != 0) {
            copy = new Vector(this.getCartesianCoordinates());
            // Rodrigues rotation formula
            this.scalarMultiplyInPlace((float) Math.cos(phiShift));
            this.additionInPlace((axisOfRotation.crossProduct(copy)).scalarMultiply((float) Math.sin(phiShift)));
            this.additionInPlace(axisOfRotation.scalarMultiply((float) ((axisOfRotation.dotProduct(copy)) * (1 - Math.cos(phiShift)))));
        }

        if (thetaShift != 0) {
            copy = new Vector(this.getCartesianCoordinates());
            Vector zAxis = new Vector(0, 0, 1);
            // Rodrigues rotation formula
            this.scalarMultiplyInPlace((float) Math.cos(thetaShift));
            this.additionInPlace((zAxis.crossProduct(copy)).scalarMultiply((float) Math.sin(thetaShift)));
            this.additionInPlace(zAxis.scalarMultiply((float) ((zAxis.dotProduct(copy)) * (1 - Math.cos(thetaShift)))));
        }
        removeRoundOffErrorBluntly();
    }

    public Vector rotationAndReturn(Float thetaShift, Float phiShift) {
        Vector vector = Vector.fromPolar(this.modulus, this.theta + thetaShift, this.phi + phiShift);
        return vector;
    }

    public void setModulus(Float modulus) {
        this.modulus = modulus;
        setCartesianCoordinates();
    }

    public void toUnitLength() {
        x = x / modulus;
        y = y / modulus;
        z = z / modulus;
        modulus = 1f;
    }

    public Vector toUnitLengthAndReturn() {
        Float[] newCartesian = new Float[3];
        Float[] thisCartesian = this.getCartesianCoordinates();
        for (int i = 0; i <3; i++) {
            newCartesian[i] = thisCartesian[i] / this.modulus;
        }
        return new Vector(newCartesian);
    }

    public void scalarModulusShift(Float shift) {
        shiftModulus(shift);
    }

    public Vector scalarModulusShiftAndReturn(Float shift) {
        Vector vector = shiftModulusAndReturn(shift);
        return vector;
    }

    public boolean samePoint(Vector otherPoint) {
        Float[] myCoords = getCartesianCoordinates();
        Float[] theirCoords = otherPoint.getCartesianCoordinates();
        for (int i = 0; i < 3; i++) {
            if (Math.abs(myCoords[i] - theirCoords[i]) >= EPSILON)
                return false;
        }
        return true;
    }

    public Vector addition(Vector otherVector) {
        Float[] newCartesian = new Float[3];
        Float[] thisCartesian = this.getCartesianCoordinates();
        Float[] otherCartesian = otherVector.getCartesianCoordinates();
        for (int i = 0; i <3; i++) {
            newCartesian[i] = thisCartesian[i] + otherCartesian[i];
        }
        return new Vector(newCartesian);
    }

    public void additionInPlace(Vector otherVector) {
        this.x = otherVector.x + this.x;
        this.y = otherVector.y + this.y;
        this.z = otherVector.z + this.z;
        setPolarCoordinates();
    }

    public Vector subtract(Vector otherVector) {
        Float[] newCartesian = new Float[3];
        Float[] thisCartesian = this.getCartesianCoordinates();
        Float[] otherCartesian = otherVector.getCartesianCoordinates();
        for (int i = 0; i <3; i++) {
            newCartesian[i] = thisCartesian[i] - otherCartesian[i];
        }
        return new Vector(newCartesian);
    }

    public void subtractInPlace(Vector otherVector) {
        this.x = this.x - otherVector.x;
        this.y = this.y - otherVector.y;
        this.z = this.z - otherVector.z;
        setPolarCoordinates();
    }

    public Float dotProduct(Vector otherVector) {
        Float dotProductScalar = 0f;
        Float[] thisCartesian = this.getCartesianCoordinates();
        Float[] otherCartesian = otherVector.getCartesianCoordinates();
        for (int i = 0; i <3; i++) {
            dotProductScalar = dotProductScalar + thisCartesian[i] * otherCartesian[i];
        }
        return dotProductScalar;
    }

    public Vector crossProduct(Vector otherVector) {
        // quick 2d cross prod
        Vector crossProduct;
        Vector i = new Vector(1, 0, 0);
        Vector j = new Vector(0, 1, 0);
        Vector k = new Vector(0, 0, 1);

        Float[] thisCartesian = this.getCartesianCoordinates();
        Float[] otherCartesian = otherVector.getCartesianCoordinates();

        i = i.scalarMultiply((thisCartesian[1] * otherCartesian[2] - thisCartesian[2] * otherCartesian[1]));
        j = j.scalarMultiply((thisCartesian[0] * otherCartesian[2] - thisCartesian[2] * otherCartesian[0]));
        k = k.scalarMultiply((thisCartesian[0] * otherCartesian[1] - thisCartesian[1] * otherCartesian[0]));

        return i.subtract(j).addition(k);
    }

    public boolean pointInTheSameDirection(Vector other) {
        return (Math.abs(this.dotProduct(other) / (this.getModulus() * other.getModulus()) - 1) < EPSILON);
    }

    public Vector rotateAroundAxisBy(Vector axisUnit, double angle) {
        // Rodrigues rotation formula
        Vector rotatedVec;
        Vector cosineTerm = this.scalarMultiply((float) Math.cos(angle));
        Vector sineTerm = (axisUnit.crossProduct(this)).scalarMultiply((float) Math.sin(angle));

        Vector dotTerm = axisUnit.scalarMultiply((float) ((axisUnit.dotProduct(this)) * (1 - Math.cos(angle))));

        rotatedVec = cosineTerm.addition(sineTerm).addition(dotTerm);
        return rotatedVec;
    }

}
