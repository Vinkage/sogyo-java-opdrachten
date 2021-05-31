package nl.sogyo.javaopdrachten.raytracer.raytracer.anglecalculator;

import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.ParametricLine;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;

public class LineLineAngleCalculator implements AngleCalculator {
    private final ParametricLine line;
    private final ParametricLine otherLine;

    public LineLineAngleCalculator(ParametricLine line, ParametricLine otherLine) {
        this.line = line;
        this.otherLine = otherLine;
    }

    public float calculateAngle() {
        Vector lineDirection = line.getDirectionVec();
        Vector otherLineDirection = otherLine.getDirectionVec();

        Float dotProduct = lineDirection.dotProduct(otherLineDirection);
        float angle = (float) Math.acos(dotProduct / (lineDirection.getModulus() * otherLineDirection.getModulus()));
        return angle;
    }
}
