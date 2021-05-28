package nl.sogyo.javaopdrachten.raytracer;

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

    public void reverseDirection() {
        directionVec = directionVec.scalarMultiply(-1f);
    }
}
