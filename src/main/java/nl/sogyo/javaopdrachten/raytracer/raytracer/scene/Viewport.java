package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Rectangle;

public class Viewport extends Rectangle {

    float[] pixelX = new float[width * height];
    float[] pixelY = new float[width * height];
    float[] pixelZ = new float[width * height];

    public Viewport(Vector[] vertices) {
        super(vertices);
        for (int i = 0; i < width * height; i++) {
            int j = i % width;
            int r = height - Math.floorDiv(i, width);
            pixelX[i] = getVector(new Coordinate(j, r)).getX();
            pixelY[i] = getVector(new Coordinate(j, r)).getY();
            pixelZ[i] = getVector(new Coordinate(j, r)).getZ();
        }
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

}
