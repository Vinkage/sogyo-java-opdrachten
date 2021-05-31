package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.exceptions.NoIntersectionPossible;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class Rectangle implements Shape {
    protected Vector corner, point, otherPoint;
    protected ParametricLine xRay, yRay;
    protected Vector normal;
    protected int width, height;

    public Rectangle(Vector[] vertices) {
        assert vertices.length == 3;
        findCornerAndSetPoints(vertices);

        setRaysAndDimensions();
        System.out.println("normal of the viewport: " + normal);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Vector[] intersect(Line line) throws NoIntersectionPossible {
        return new Vector[0];
    }

    private void setRaysAndDimensions() {
        setXRayToLongestAndYRayToShortestSide();

        normal = xRay.getDirectionVec().crossProduct(yRay.getDirectionVec());
        float phi = normal.getPhi();
        float theta = normal.getTheta();


        Vector z;
        z = new Vector(0,0,1);

        float toZPhi = z.getPhi() - phi;
        float toZTheta = z.getTheta() - theta;
        System.out.println(normal.dotProduct(z) + " " + toZPhi + " " + toZTheta);
        if (normal.dotProduct(z) < 0) {
            toZPhi = toZPhi - (float) Math.PI;
        }
        System.out.println("normal");
        System.out.println(this);
        normal.rotationInPlace(toZTheta, toZPhi);
        System.out.println(this + "\n");

        System.out.println("x");
        System.out.println(this);
        xRay.getDirectionVec().rotationInPlace(toZTheta, toZPhi);
        System.out.println(this + "\n");

        System.out.println("y");
        System.out.println(this);
        yRay.getDirectionVec().rotationInPlace(toZTheta, toZPhi);
        System.out.println(this + "\n");

        System.out.println("corner");
        System.out.println(this);
        corner.rotationInPlace(toZTheta, toZPhi);
        System.out.println(this + "\n");



        Vector y;
        y = new Vector(0,1,0);
        float toYTheta = y.getTheta() - yRay.getDirectionVec().getTheta();
        if (y.dotProduct(yRay.getDirectionVec()) < 0) {
            toYTheta = (float) (toYTheta - Math.PI);
        }

        System.out.println("Rotating Y to y");
        System.out.println(this);
        yRay.getDirectionVec().rotationInPlace(toYTheta, 0f);
        System.out.println(this + "\n");

        System.out.println("Rotating X accordingly");
        System.out.println(this);
        xRay.getDirectionVec().rotationInPlace(toYTheta, 0f);
        System.out.println(this + "\n");

        System.out.println("Rotating corner accordingly");
        System.out.println(this);
        corner.rotationInPlace(toYTheta, 0f);
        System.out.println(this + "\n");
        chooseTopLeftCorner();

        // TODO reverse rotations
    }

    private void chooseTopLeftCorner() {
        if (xRay.getDirectionVec().getX() < 0) {
            corner.additionInPlace(xRay.getDirectionVec().scalarMultiply((float) width));
            xRay.getDirectionVec().scalarMultiplyInPlace(-1f);
        }
        if (yRay.getDirectionVec().getY() > 0) {
            corner.additionInPlace(yRay.getDirectionVec().scalarMultiply((float) height));
            yRay.getDirectionVec().scalarMultiplyInPlace(-1f);
        }
    }

    public String toString() {
        return "corner: (" + corner.getX() + ", " + corner.getY() + ", " + corner.getZ() + ") " +
                ", x direction: (" + xRay.getDirectionVec().getX() + ", " + xRay.getDirectionVec().getY() + ", " + xRay.getDirectionVec().getZ() + ") " +
                ", y direction: (" + yRay.getDirectionVec().getX() + ", " + yRay.getDirectionVec().getY() + ", " + yRay.getDirectionVec().getZ() + ") " +
                ", normal: (" + normal.getX() + ", " + normal.getY() + ", " + normal.getZ() + ") " +
                ", WidthxHeight: " + width + "x" + height;
    }

    private void setXRayToLongestAndYRayToShortestSide() {
        Vector edge = point.subtract(corner);
        Vector otherEdge = otherPoint.subtract(corner);
        if (edge.getModulus() >= otherEdge.getModulus()) {
            xRay = new Line(corner, point).parametricRepresentation();
            width = Math.round(edge.getModulus());

            yRay = new Line(corner, otherPoint).parametricRepresentation();
            height = Math.round(otherEdge.getModulus());

        } else {
            yRay = new Line(corner, point).parametricRepresentation();
            height = Math.round(edge.getModulus());

            xRay = new Line(corner, otherPoint).parametricRepresentation();
            width = Math.round(otherEdge.getModulus());
        }
    }

    private void findCornerAndSetPoints(Vector[] vertices) {
        if (vertices[1].subtract(vertices[0]).dotProduct(vertices[2].subtract(vertices[0])) == 0) {
            corner = vertices[0];
        } else if (vertices[0].subtract(vertices[1]).dotProduct(vertices[2].subtract(vertices[1])) == 0) {
            corner = vertices[1];
        } else {
            corner = vertices[2];
        }
        ArrayList<Vector> verticesList = new ArrayList<>(Arrays.asList(vertices));
        verticesList.remove(corner);
        point = verticesList.remove(0);
        otherPoint = verticesList.remove(0);
    }
}
