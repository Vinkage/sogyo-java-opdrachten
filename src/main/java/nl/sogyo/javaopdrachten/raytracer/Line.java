package nl.sogyo.javaopdrachten.raytracer;

import java.util.Arrays;
import java.util.List;

public class Line {
    private Vector origin;
    private Vector bindingPoint;
    private static final double EPSILON = 1e-8;

    public Line(Vector origin, Vector bindingPoint) {
        this.origin = origin;
        this.bindingPoint = bindingPoint;
    }

    public ParametricLine parametricRepresentation() {
        Vector directionUnitVec = origin.subtraction(bindingPoint).toUnitLengthAndReturn();
        System.out.println(directionUnitVec.toString());
        return new ParametricLine(this.origin, directionUnitVec);
    }

    public Intersection intersect(Line otherLine) {
        // We get the coordinates of the origins of the two lines
        Float[] originxyz = this.origin.getCartesianCoordinates();
        Float[] otherOriginxyz = otherLine.origin.getCartesianCoordinates();

        // Then we get the direction vectors
        Float[] directionxyz = this.parametricRepresentation().getDirectionVec().getCartesianCoordinates();
        Float[] otherDirectionxyz = otherLine.parametricRepresentation().getDirectionVec().getCartesianCoordinates();

        // Since we have two lines in parameterized form, we need to solve for two parameters,
        // so we want to solve only a system of two equations for the two parameters

        // However, the lines are three dimensional, thus we want to look at the lines only in
        // one of the three planes (xy, xz, yz) to get a two dimensional system.
        // There is the edge case where the two lines only exist in onne of these planes in the 3D space,
        // so we need to check if the corresponding coordinates are zero in that dimension or not

        double[][] A;
        double[] b;
        A = new double[][]{{directionxyz[0], otherDirectionxyz[0]}, {directionxyz[1], otherDirectionxyz[1]}};

        Double[] originConstant = new Double[3];
        for (int i = 0; i < 3; i++)
            originConstant[i] = (double) otherOriginxyz[i] - originxyz[i];

        b = new double[]{originConstant[0], originConstant[1]};


        List<Float> direction = Arrays.asList(directionxyz);
        List<Float> otherDirection = Arrays.asList(otherDirectionxyz);
        List<Double> originConstantList = Arrays.asList(originConstant);
        // if both in same plane, then no choice but to use that planes components of the directions
        // If not both in same plane, but have same direction in some plane, don't use that plane
        for (int i=0; i < directionxyz.length; i++) {
            if (Math.abs(directionxyz[i]) < EPSILON && Math.abs(otherDirectionxyz[i]) < EPSILON) {
                direction.remove(i);
                otherDirection.remove(i);
                originConstantList.remove(i);
                A = new double[][]{direction.stream().mapToDouble(k -> k).toArray(), otherDirection.stream().mapToDouble(k -> k).toArray()};
                b = originConstantList.stream().mapToDouble(k -> k).toArray();
            } for (int j = 1; j < (directionxyz.length - i); i++) {
                if (Math.abs(directionxyz[i] - otherDirectionxyz[i]) < EPSILON && Math.abs(directionxyz[i+j] - otherDirectionxyz[i+j]) < EPSILON) {
                    direction.remove(i);
                    otherDirection.remove(i);
                    originConstantList.remove(i);
                    A = new double[][]{direction.stream().mapToDouble(k -> k).toArray(), otherDirection.stream().mapToDouble(k -> k).toArray()};
                    b = originConstantList.stream().mapToDouble(k -> k).toArray();
                }
            }
        }


        GaussJordanElimination systemSolver = new GaussJordanElimination(A, b);

        try {
            double[] solvedParams = systemSolver.primal();
            Vector intersectionPoint = this.parametricRepresentation().getVectorOfPointOnLine((float) solvedParams[0]);
            AngleCalculator angleCalculator = new AngleCalculator(this.parametricRepresentation(), otherLine.parametricRepresentation());
            Float angle = angleCalculator.calculateAngle();
            return new Intersection(intersectionPoint, angle);
        } catch (NullPointerException e) {
            System.out.println("The system could not be solved!");
        }
        return null;
    }
}
