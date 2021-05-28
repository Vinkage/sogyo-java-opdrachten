package nl.sogyo.javaopdrachten.raytracer;

public class Viewport {
    private Vector corner;
    private ParametricLine xRay, yRay;
    private int width, height;

    public Viewport(Vector[] vertices) {
        Vector edge = null;
        Vector otherEdge = null;
        if (vertices.length == 3) {
            if (vertices[1].subtraction(vertices[0]).dotProduct(vertices[2].subtraction(vertices[0])) == 0) {
                corner = vertices[0];
                edge =  vertices[1].subtraction(corner);
                otherEdge=  vertices[2].subtraction(corner);
                xRay = new Line(corner, vertices[1]).parametricRepresentation();
                yRay = new Line(corner, vertices[2]).parametricRepresentation();
            } else if (vertices[0].subtraction(vertices[1]).dotProduct(vertices[2].subtraction(vertices[1])) == 0) {
                corner = vertices[1];
                edge =  vertices[0].subtraction(corner);
                otherEdge=  vertices[2].subtraction(corner);
                xRay = new Line(corner, vertices[0]).parametricRepresentation();
                yRay = new Line(corner, vertices[2]).parametricRepresentation();
            } else {
                corner = vertices[2];
                edge =  vertices[0].subtraction(corner);
                otherEdge=  vertices[1].subtraction(corner);
                xRay = new Line(corner, vertices[0]).parametricRepresentation();
                yRay = new Line(corner, vertices[1]).parametricRepresentation();
            }

            if (Math.abs(edge.crossProduct(otherEdge).getModulus()) < 1e-8)
                try {
                    throw new Exception("There is no rectangle with these vertices");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            // set xray to the one with the smallest y component, assuming y is the vertical
            width = (int) Math.abs(edge.getModulus());
            height = (int) Math.abs(otherEdge.getModulus());

            if (Math.abs(xRay.getDirectionVec().getY()) > Math.abs(yRay.getDirectionVec().getY())) {
                // Switch width and height
                int tmpWidth = width;
                width = height;
                height = tmpWidth;

                // Switch rays x and y
                ParametricLine tmp = xRay;
                xRay = yRay;
                yRay = tmp;
            }

           // Make it so the corner point is the one with positive directions to other points
           if (xRay.getDirectionVec().getX() < 0) {
               corner = corner.addition(xRay.getVectorOfPointOnLine((float) width).subtraction(xRay.getOrigin()));
               xRay.reverseDirection();
               xRay = new Line(corner, corner.addition(xRay.getDirectionVec())).parametricRepresentation();
           }

           if (yRay.getDirectionVec().getY() < 0) {
               corner = corner.addition(yRay.getVectorOfPointOnLine((float) height).subtraction(yRay.getOrigin()));
               yRay.reverseDirection();
               yRay = new Line(corner, corner.addition(yRay.getDirectionVec())).parametricRepresentation();
           }

            xRay = new Line(corner, corner.addition(xRay.getDirectionVec())).parametricRepresentation();
            yRay = new Line(corner, corner.addition(yRay.getDirectionVec())).parametricRepresentation();

            try {
               if (xRay.getOrigin().samePoint(yRay.getOrigin()));
               else throw new NotTheSameOrigin();
           } catch (NotTheSameOrigin e) {
               e.printStackTrace();
           }

        }
    }

    Vector getVector(Coordinate coord) {
        Vector vector = null;

        int x = coord.getX();
        int y = coord.getY();

        Vector xAsPos = xRay.getVectorOfPointOnLine((float) x);
        Vector yAsPos = yRay.getVectorOfPointOnLine((float) y);

        vector = yAsPos.addition(xAsPos.subtraction(corner));
        return vector;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String toString() {
        return "corner: (" + corner.getX() + ", " + corner.getY() + ", " + corner.getZ() + ") " +
                ", x direction: (" + xRay.getDirectionVec().getX() + ", " + xRay.getDirectionVec().getY() + ", " + xRay.getDirectionVec().getZ() + ") " +
                ", y direction: (" + yRay.getDirectionVec().getX() + ", " + yRay.getDirectionVec().getY() + ", " + yRay.getDirectionVec().getZ() + ") " +
                ", WidthxHeight: " + width + "x" + height;
    }

}
