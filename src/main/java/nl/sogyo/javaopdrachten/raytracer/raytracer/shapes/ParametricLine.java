package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;

public class ParametricLine {
    private Vector origin;
    private Vector directionVec;

    public ParametricLine(Vector origin, Vector directionVec) {
        this.origin = origin;
        this.directionVec = directionVec;
    }

    public Vector getDirectionVec() {
        return directionVec;
    }

    public Vector getOrigin() {
        return origin;
    }

    public Vector getVectorOfPointOnLine(Float parameter) {
        Vector step = directionVec.scalarMultiply(parameter);
        return origin.addition(step);
    }

}
