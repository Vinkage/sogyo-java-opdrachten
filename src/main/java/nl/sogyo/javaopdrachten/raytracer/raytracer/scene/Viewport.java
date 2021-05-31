package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Rectangle;

public class Viewport extends Rectangle {

    public Viewport(Vector[] vertices) {
        super(vertices);
    }


    Vector getVector(Coordinate coord) {
        Vector vector = null;

        int x = coord.getX();
        int y = coord.getY();

        Vector xAsPos = xRay.getVectorOfPointOnLine((float) x);
        Vector yAsPos = yRay.getVectorOfPointOnLine((float) y);

        vector = yAsPos.addition(xAsPos.subtract(corner));
        return vector;
    }


    public void orientToViewPoint(Vector viewpoint) {

    }
}
