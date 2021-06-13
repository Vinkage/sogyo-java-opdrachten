package nl.sogyo.javaopdrachten.raytracer.raytracer.anglecalculator;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Line;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Rectangle;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Sphere;

public class AngleCalculator {
    public float calculateAngle(Line line, Line otherLine) {
        Vector lineDirection = line.parametric().direction();
        Vector otherLineDirection = otherLine.parametric().direction();

        Float dotProduct = lineDirection.dotProduct(otherLineDirection);
        float angle = (float) Math.acos(dotProduct / (lineDirection.getModulus() * otherLineDirection.getModulus()));
        return angle;
    }

    public float calculateAngle(Vector vec, Vector otherVec) {
        Float dotProduct = vec.dotProduct(otherVec);
        float angle = (float) Math.acos(dotProduct / (vec.getModulus() * otherVec.getModulus()));
        return angle;
    }

    public float calculateAngle(Line line, Rectangle rectangle) {
        Vector rectangleNormal = rectangle.getNormal();
        Vector lineDirection = line.parametric().direction();

        float dot = rectangleNormal.dotProduct(lineDirection);
        float angle = (float) Math.acos(dot / (lineDirection.getModulus() * rectangleNormal.getModulus()));
        return angle;
    }

    public float calculateAngle(Line line, Sphere sphere, Vector intersectionPoint) {
        Vector sphereCenter = sphere.getOrigin();
        Vector sphereNormal = intersectionPoint.subtract(sphereCenter).toUnitLengthAndReturn();

        Vector lineDirection = line.parametric().direction();

        float dot = sphereNormal.dotProduct(lineDirection);
        float angle = (float) Math.acos(dot / (lineDirection.getModulus() * sphereNormal.getModulus()));
        return angle;
    }
}

