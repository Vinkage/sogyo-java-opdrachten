package nl.sogyo.javaopdrachten.raytracer;

public class Line {
    private Vector origin;
    private Vector bindingPoint;

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
        Float[] originxyz = this.origin.getCartesianCoordinates();
        Float[] otherOriginxyz = otherLine.origin.getCartesianCoordinates();

        Float[] directionxyz = this.parametricRepresentation().getDirectionVec().getCartesianCoordinates();
        Float[] otherDirectionxyz = otherLine.parametricRepresentation().getDirectionVec().getCartesianCoordinates();

        double[] originB = new double[2];
        for (int i = 0; i<1; i++)
            originB[i] = (double) otherOriginxyz[i] - originxyz[i];

        double[][] parameterMatrixA = new double[2][2];
        for (int i=0; i < parameterMatrixA.length; i++) {
            for (int j=0; j < parameterMatrixA.length; j++) {
                if (j == 0) {
                    parameterMatrixA[i][j] = (double) directionxyz[j];
                } else if (j == 1) {
                    parameterMatrixA[i][j] = - (double) otherDirectionxyz[j];
                }
            }
        }

        GaussJordanElimination systemSolver = new GaussJordanElimination(parameterMatrixA, originB);

        double[] solvedParams = systemSolver.primal();

        Float[] intersectionCoords;
        for (int i =0; i < solvedParams.length; i++) {
            System.out.println(solvedParams[i]);
        }

        Vector intersectionPoint = this.parametricRepresentation().getVectorOfPointOnLine((float) solvedParams[0]);
        AngleCalculator angleCalculator = new AngleCalculator(this.parametricRepresentation(), otherLine.parametricRepresentation());
        Float angle = angleCalculator.calculateAngle();
        return new Intersection(intersectionPoint, angle);
    }


}
