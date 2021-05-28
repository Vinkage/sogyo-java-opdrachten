package nl.sogyo.javaopdrachten.raytracer;

public class RayTracer {

    public static void main(String[] args) {
        RayTracer rayTracer = new RayTracer();

        // rayTracer.vectorConstructionTestDrive();
        // rayTracer.vectorScalarOperations();
        // rayTracer.vectorVectorOperations();

        // rayTracer.lineTestDrive();
        // rayTracer.angleCalculatorTestDrive();
        // rayTracer.lineIntersecterTestDrive();

        // rayTracer.sphereTestDrive();

        // rayTracer.viewPortTestDrive();

        // rayTracer.lightSourceTestDrive();

        rayTracer.sceneTestDrive();
    }

    private void sceneTestDrive() {
        System.out.println("\n\nTesting scene construction");
        Vector[] vertices = new Vector[] {
                new Vector(-400, 300, 50),
                new Vector(400, 300, 50),
                new Vector(400, -300, 50),
        };
        Scene scene = new Scene(
                new Vector(0, 0, 0),
                new Viewport(vertices),
                new Lightsource[] {
                        new Lightsource(100, new Vector(500, 500, 155)),
                        new Lightsource(50, new Vector(500, -100, 75)),
                },
                new Shape[] {
                        new Sphere(new Vector(0, 0, 100), 200),
                        new Sphere(new Vector(100, 150, 130), 50),
                }
        );

        System.out.println("\tExample scene: ");
        System.out.println(scene);



        float[][] pixels = scene.getProjection();

        for (float[] row: pixels) {
            for (float pixel: row) {
                if (pixel> 0)
                    System.out.println(pixel);
            }
        }
    }

    // private void lightSourceTestDrive() {
    //     System.out.println("\n\nTesting lightsource construction");
    //     Lightsource lightsource = new Lightsource(100, new Vector(500, 500, 155));
    //     Lightsource lightsource2 = new Lightsource(50, new Vector(500, -100, 75));

    //     System.out.println("\t" + lightsource);
    //     System.out.println("\t" + lightsource2);
    // }

    // private void viewPortTestDrive() {
    //     System.out.println("\n\nTesting viewport construction");
    //     Vector[] vertices = new Vector[] {
    //             new Vector(-400, 300, 50),
    //             new Vector(400, 300, 50),
    //             new Vector(400, -300, 50),
    //     };
    //     Viewport viewport = new Viewport(vertices);
    //     System.out.println("\t" + viewport.toString());

    //     System.out.println("\tviewport.getVector(new Coordinate(10, 13))");
    //     System.out.println("\t" + viewport.getVector(new Coordinate(10, 13)));
    // }

    // private void sphereTestDrive() {
    //     System.out.println("\n\nTesting line-sphere intersection calculator");
    //     Vector origin = new Vector(0,0,0);
    //     Float radius = 5f;
    //     Sphere sphere = new Sphere(origin, radius);

    //     System.out.println("\t" + sphere);

    //     Vector rayOrigin = new Vector(-6, -6, -6);
    //     Vector rayDefiner = new Vector(6, 6, 6);
    //     Line rayLine = new Line(rayOrigin, rayDefiner);
    //     ParametricLine ray = rayLine.parametricRepresentation();

    //     Vector[] intersections = sphere.intersect(rayLine);
    //     for (Vector intersection : intersections)
    //         System.out.println("\tIntersection: " + intersection.toString());

    //     rayOrigin = new Vector(10,10,5);
    //     rayDefiner = new Vector(-10,-10,5);
    //     rayLine = new Line(rayOrigin, rayDefiner);

    //     Vector[] intersectionsSingleton = sphere.intersect(rayLine);
    //     System.out.print("\tShould be a singleton -> ");
    //     for (Vector intersection : intersectionsSingleton)
    //         System.out.println("\tIntersection: " + intersection.toString());
    // }

    // private void lineIntersecterTestDrive() {
    //     System.out.println("\n\nTesting line-line intersection calculator");
    //     System.out.println("\tExample 1:");
    //     Vector vector = new Vector(2,1,-2);
    //     Vector otherVector = new Vector(8,-7,8);
    //     Line line = new Line(vector, otherVector);

    //     Vector vector1 = new Vector(-1,-10,5);
    //     Vector otherVector1 = new Vector(5, -3,3);
    //     Line otherLine = new Line(vector1, otherVector1);

    //     // Should be (5, -3, 3)
    //     Intersection intersection = line.intersect(otherLine);
    //     System.out.println("\t" + intersection);

    //     System.out.println("\tExample 2:");
    //     Vector vector2 = new Vector(1,4,-2);
    //     Vector otherVector2 = new Vector(3,8,6);
    //     Line line2 = new Line(vector2, otherVector2);

    //     Vector vector21 = new Vector(0,4,8);
    //     Vector otherVector21 = new Vector(6, 10,-10);
    //     Line otherLine2 = new Line(vector21, otherVector21);

    //     // Should be (2, 6, 2)
    //     Intersection intersection2 = line2.intersect(otherLine2);
    //     System.out.println("\t" + intersection2);
    // }

    // private void angleCalculatorTestDrive() {
    //     System.out.println("\n\nTesting angle calculator");
    //     Vector vector = new Vector(1,2,3);
    //     Vector otherVector = new Vector(4,5,6);
    //     Line line = new Line(vector, otherVector);
    //     ParametricLine parametricLine = line.parametricRepresentation();

    //     Vector vector1 = new Vector(7,8,9);
    //     Vector otherVector1 = new Vector(20,-11,2);
    //     Line otherLine = new Line(vector1, otherVector1);
    //     ParametricLine otherParametricLine = otherLine.parametricRepresentation();

    //     AngleCalculator angleCalculator = new AngleCalculator(parametricLine, otherParametricLine);

    //     Float angle = angleCalculator.calculateAngle();
    //     System.out.println("\t" + angle + "(radians)");
    // }

    // private void lineTestDrive() {
    //     Vector vector = new Vector(1,2,3);
    //     Vector otherVector = new Vector(4,5,6);
    //     Line line = new Line(vector, otherVector);
    //     ParametricLine parametricLine = line.parametricRepresentation();
    // }

    // private void vectorVectorOperations() {
    //     System.out.println("\n\nVector vector operations");
    //     Vector vector = new Vector(1,2,3);
    //     Vector otherVector = new Vector(4,5,6);

    //     // addition
    //     Vector addedVector = vector.addition(otherVector);
    //     System.out.println("\t" + addedVector.toString());
    //     addedVector = vector.subtraction(otherVector);
    //     System.out.println("\t" + addedVector.toString());

    //     // dot
    //     Float dotScalar = vector.dotProduct(otherVector);
    //     System.out.println("\t" + dotScalar);

    //     // cross
    //     System.out.println("Computing cross product");
    //     vector = new Vector(1,2,3);
    //     otherVector = new Vector(4,5,6);
    //     Vector crossProduct = vector.crossProduct(otherVector);
    //     System.out.println("\t" + crossProduct.toString());
    // }

    // private void vectorScalarOperations() {
    //     System.out.println("\n\nScalar vector operations");
    //     Vector vector = new Vector(1,2,3);
    //     System.out.println(vector.toString());

    //     // in place shift and scale modulus, and rotate
    //     vector.scalarModulusShift(3f);
    //     System.out.println(vector.toString());
    //     vector.scaleModulus(3f);
    //     System.out.println(vector.toString());
    //     vector.rotation(3f, 3f);
    //     System.out.println(vector.toString());

    //     // shift and scale producing new vec, and rotate
    //     Vector newVec;
    //     // newVec = vector.scaleModulusAndReturn(3f);
    //     newVec = vector.scalarModulusShiftAndReturn(3f);
    //     System.out.println(newVec.toString());
    //     newVec = vector.scalarModulusShiftAndReturn(3f);
    //     System.out.println(newVec.toString());
    //     newVec = vector.rotationAndReturn(3f, 0f);
    //     System.out.println(newVec.toString());

    //     // To unit vec
    //     newVec = vector.toUnitLengthAndReturn();
    //     System.out.println(newVec.toString());
    //     vector.toUnitLength();
    //     System.out.println(vector.toString());
    // }

    // private void vectorConstructionTestDrive() {
    //     // using cartesian ints
    //     Vector vector = new Vector(1, 2,3);
    //     System.out.println("Made a new vec using integer cartesian coords: \n\t" + vector.toString());
    //     // using cartesian doubles
    //     vector = new Vector(1.0, 2.0,3.0);
    //     System.out.println("Made a new vec using double cartesian coords: \n\t" + vector.toString());
    //     // using cartesian floats
    //     vector = new Vector(1.0f, 2.0f,3.0f);
    //     System.out.println("Made a new vec using floats cartesian coords: \n\t" + vector.toString());
    //     // using float polar coords
    //     vector = Vector.fromPolar(3.0f, 1.0f, 1.0f);
    //     System.out.println("Made a new vec using float polar coords: \n\t" + vector.toString());
    // }
}
