package nl.sogyo.javaopdrachten.raytracer;

public class Vector {
    private Float x, y, z;
    private Float modulus;
    private Float theta, phi;
    private static final double EPSILON = 1e-8;

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
        this.y = (float) (this.modulus * Math.sin(this.phi) * Math.sin(this.theta));
        this.z = (float) (this.modulus * Math.cos(this.phi));
    }

    public String toString() {
        return ("x :" + this.x + ", y: " + this.y + ", z: " + this.z + ", mod: " + this.modulus + ", theta: " + this.theta + ", phi: " + this.phi);
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
        if (x == 0) {
            this.theta = (float) Math.PI /2;
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
        this.modulus = scalar * this.modulus;
        this.setCartesianCoordinates();
    }

    public Vector scaleModulusAndReturn(Float scalar) {
        Vector vector;
        vector = Vector.fromPolar((scalar * this.modulus), this.theta, this.phi);
        return vector;
    }

    public Float[] getCartesianCoordinates() {
        return new Float[]{this.x, this.y, this.z};
    }

    public Float[] getPolarCoordinates() {
        return new Float[]{this.modulus, this.theta, this.phi};
    }

    public void rotation(Float thetaShift, Float phiShift) {
        if (this.theta == null) {
            this.theta = 0f;
        }
        if (this.phi == null) {
            this.phi = 0f;
        }
        this.theta = this.theta + thetaShift;
        this.phi = this.phi + phiShift;
        setCartesianCoordinates();
    }

    public Vector rotationAndReturn(Float thetaShift, Float phiShift) {
        Vector vector = Vector.fromPolar(this.modulus, this.theta + thetaShift, this.phi + phiShift);
        return vector;
    }

    public void setModulus(Float modulus) {
        this.modulus = modulus;
        setCartesianCoordinates();
    }

    public Vector setModulusAndReturn(Float modulus) {
        return Vector.fromPolar(modulus, this.theta, this.phi);
    }

    public void toUnitLength() {
        this.setModulus(1f);
    }

    public Vector toUnitLengthAndReturn() {
        return setModulusAndReturn(1f);
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

    public Vector subtraction(Vector otherVector) {
        Float[] newCartesian = new Float[3];
        Float[] thisCartesian = this.getCartesianCoordinates();
        Float[] otherCartesian = otherVector.getCartesianCoordinates();
        for (int i = 0; i <3; i++) {
            newCartesian[i] = thisCartesian[i] - otherCartesian[i];
        }
        return new Vector(newCartesian);
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

        i.scaleModulus((thisCartesian[1] * otherCartesian[2] - thisCartesian[2] * otherCartesian[1]));
        j.scaleModulus((thisCartesian[0] * otherCartesian[2] - thisCartesian[2] * otherCartesian[0]));
        k.scaleModulus((thisCartesian[0] * otherCartesian[1] - thisCartesian[1] * otherCartesian[0]));

        return i.subtraction(j).addition(k);
    }

}
