package nl.sogyo.javaopdrachten.raytracer;

public class RayTracer {

    public static void main(String[] args) {
        RayTracer rayTracer = new RayTracer();

        rayTracer.vectorConstructionTestDrive();
        rayTracer.vectorScalarOperations();
        rayTracer.vectorVectorOperations();

        rayTracer.lineTestDrive();
        rayTracer.angleCalculatorTestDrive();
        rayTracer.lineIntersecterTestDrive();

        rayTracer.sphereTestDrive();
    }

    private void sphereTestDrive() {
        // http://paulbourke.net/geometry/circlesphere/
    }

    private void lineIntersecterTestDrive() {
        System.out.println("\n\nTesting intersection calculator");
        Vector vector = new Vector(1,1,1);
        Vector otherVector = new Vector(2,2,2);
        Line line = new Line(vector, otherVector);
        Vector vector1 = new Vector(1,1,1);
        Vector otherVector1 = new Vector(4, 5,2);
        Line otherLine = new Line(vector1, otherVector1);

        Vector intersection = line.intersect(otherLine);

    }

    private void angleCalculatorTestDrive() {
        System.out.println("\n\nTesting angle calculator");
        Vector vector = new Vector(1,2,3);
        Vector otherVector = new Vector(4,5,6);
        Line line = new Line(vector, otherVector);
        ParametricLine parametricLine = line.parametricRepresentation();

        Vector vector1 = new Vector(7,8,9);
        Vector otherVector1 = new Vector(20,-11,2);
        Line otherLine = new Line(vector1, otherVector1);
        ParametricLine otherParametricLine = otherLine.parametricRepresentation();

        AngleCalculator angleCalculator = new AngleCalculator(parametricLine, otherParametricLine);

        Float angle = angleCalculator.calculateAngle();
        System.out.println(angle + "(radians)");
    }

    private void lineTestDrive() {
        Vector vector = new Vector(1,2,3);
        Vector otherVector = new Vector(4,5,6);
        Line line = new Line(vector, otherVector);
        ParametricLine parametricLine = line.parametricRepresentation();
    }

    private void vectorVectorOperations() {
        System.out.println("\n\nVector vector operations");
        Vector vector = new Vector(1,2,3);
        Vector otherVector = new Vector(4,5,6);

        // addition
        Vector addedVector = vector.addition(otherVector);
        System.out.println(addedVector.toString());
        addedVector = vector.subtraction(otherVector);
        System.out.println(addedVector.toString());

        // dot
        Float dotScalar = vector.dotProduct(otherVector);
        System.out.println(dotScalar);

        // cross
        System.out.println("Computing cross product");
        vector = new Vector(1,2,3);
        otherVector = new Vector(4,5,6);
        Vector crossProduct = vector.crossProduct(otherVector);
        System.out.println(crossProduct.toString());
    }

    private void vectorScalarOperations() {
        System.out.println("\n\nScalar vector operations");
        Vector vector = new Vector(1,2,3);
        System.out.println(vector.toString());

        // in place shift and scale modulus, and rotate
        vector.scalarModulusShift(3f);
        System.out.println(vector.toString());
        vector.scaleModulus(3f);
        System.out.println(vector.toString());
        vector.rotation(3f, 3f);
        System.out.println(vector.toString());

        // shift and scale producing new vec, and rotate
        Vector newVec;
        newVec = vector.scaleModulusAndReturn(3f);
        System.out.println(newVec.toString());
        newVec = vector.scalarModulusShiftAndReturn(3f);
        System.out.println(newVec.toString());
        newVec = vector.rotationAndReturn(3f, 0f);
        System.out.println(newVec.toString());

        // To unit vec
        newVec = vector.toUnitLengthAndReturn();
        System.out.println(newVec.toString());
        vector.toUnitLength();
        System.out.println(vector.toString());
    }

    private void vectorConstructionTestDrive() {
        // using cartesian ints
        Vector vector = new Vector(1, 2,3);
        System.out.println("Made a new vec using integer cartesian coords: \n\t" + vector.toString());
        // using cartesian doubles
        vector = new Vector(1.0, 2.0,3.0);
        System.out.println("Made a new vec using double cartesian coords: \n\t" + vector.toString());
        // using cartesian floats
        vector = new Vector(1.0f, 2.0f,3.0f);
        System.out.println("Made a new vec using floats cartesian coords: \n\t" + vector.toString());
        // using float polar coords
        vector = Vector.fromPolar(3.0f, 1.0f, 1.0f);
        System.out.println("Made a new vec using float polar coords: \n\t" + vector.toString());
    }
}
