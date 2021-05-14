package nl.sogyo.javaopdrachten.raytracer;

public class AngleCalculator {
    private ParametricLine line;
    private ParametricLine otherLine;

    public AngleCalculator(ParametricLine line, ParametricLine otherLine) {
        this.line = line;
        this.otherLine = otherLine;
    }

    public Float calculateAngle() {
        Vector lineDirection = line.getDirectionVec();
        Vector otherLineDirection = otherLine.getDirectionVec();

        Float dotProduct = lineDirection.dotProduct(otherLineDirection);
        Float angle = (float) Math.acos(dotProduct / (lineDirection.getModulus() * otherLineDirection.getModulus()));
        return angle;
    }
}
