package nl.sogyo.javaopdrachten.raytracer.raytracer.shapes;

import nl.sogyo.javaopdrachten.raytracer.raytracer.anglecalculator.AngleCalculator;
import nl.sogyo.javaopdrachten.raytracer.raytracer.exceptions.NoIntersectionPossible;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;

import java.util.ArrayList;
import java.util.Arrays;

public class Rectangle implements Shape {
    protected Vector corner, point, otherPoint;
    protected ParametricLine xRay, yRay;
    protected Vector normal;
    protected int width, height;
    private static final double EPSILON = 1e-8;

    public Rectangle(Vector[] vertices) {
        assert vertices.length == 3;
        findCornerAndSetPoints(vertices);

        setRaysAndDimensions();
        // System.out.println("normal of the viewport: " + normal);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public Vector[] intersect(Line line) throws NoIntersectionPossible {
        ParametricLine ray = line.parametric();
        Vector rayOrigin = ray.getOrigin();
        Vector rayDirection = ray.direction();

        float normaalDotRayDirection = normal.dotProduct(rayDirection);

        if (Math.abs(normaalDotRayDirection) < EPSILON) throw new NoIntersectionPossible(ray, this);

        float scalaireProjectieAfstandOpNormaal = corner.subtract(rayOrigin).dotProduct(normal);

        float rayRichtingsGrootteInNormaalRichting = rayDirection.dotProduct(normal);

        float scalarOmPuntTeBereiken = scalaireProjectieAfstandOpNormaal / rayRichtingsGrootteInNormaalRichting;

        Vector vectorVanRayOriginTotPuntInRechtoekVlak = rayDirection.scalarMultiply(scalarOmPuntTeBereiken);

        Vector puntInRechthoekVlak = rayOrigin.addition(vectorVanRayOriginTotPuntInRechtoekVlak);

        float afstandTussenHoekEnPuntInRechthoekVlakX = puntInRechthoekVlak.subtract(corner).dotProduct(xRay.direction());
        float afstandTussenHoekEnPuntInRechthoekVlakY = puntInRechthoekVlak.subtract(corner).dotProduct(yRay.direction());

        float edgeX = xRay.direction().scalarMultiply((float) width).getModulus();
        float edgeY = yRay.direction().scalarMultiply((float) height).getModulus();

        if (0 <= afstandTussenHoekEnPuntInRechthoekVlakX && afstandTussenHoekEnPuntInRechthoekVlakX <= edgeX &&
                0 <= afstandTussenHoekEnPuntInRechthoekVlakY && afstandTussenHoekEnPuntInRechthoekVlakY <= edgeY) {
            return new Vector[] {puntInRechthoekVlak};
        }

        throw new NoIntersectionPossible(ray, this);
    }

    @Override
    public Float calculateAngle(Line line, Vector nearestIntersectionPoint) {
        AngleCalculator angleCalculator = new AngleCalculator();
        return angleCalculator.calculateAngle(line, this);
    }

    private void setRaysAndDimensions() {
        setXRayToLongestAndYRayToShortestSide();

        normal = xRay.direction().crossProduct(yRay.direction());
        Vector originalNormal = new Vector(normal.getCartesianCoordinates());
        Vector phiRotationAxis = normal.crossProduct(new Vector(0, 0, 1));

        float phi = normal.getPhi();
        float theta = normal.getTheta();

        Vector z;
        z = new Vector(0,0,1);

        Vector y;
        y = new Vector(0,1,0);

        //rotating to the xy plane
        float toZPhi = z.getPhi() - phi;
        float toZTheta = z.getTheta() - theta;

        // check which direction the normal is compared to the z dimension of the space
        if (normal.dotProduct(z) < 0) {
            toZPhi = toZPhi - (float) Math.PI;
        }

        rotateRectangle(toZTheta, toZPhi, phiRotationAxis);

        // check if in the sine principal region, correct otherwise
        float toYTheta = y.getTheta() - yRay.direction().getTheta();
        // if (y.dotProduct(yRay.getDirectionVec()) < 0) {
        //     toYTheta = (float) (toYTheta - Math.PI);
        // }

        rotateRectangle(toYTheta, 0f, phiRotationAxis);
        chooseTopLeftCorner();
        rotateRectangle(-toYTheta, 0f, phiRotationAxis);

        rotateRectangle(originalNormal.getTheta() - normal.getTheta(), originalNormal.getPhi() - normal.getPhi(), phiRotationAxis.scalarMultiply(-1f));
    }

    private void chooseTopLeftCorner() {
        if (xRay.direction().getX() < 0) {
            corner.additionInPlace(xRay.direction().scalarMultiply((float) width));
            xRay.direction().scalarMultiplyInPlace(-1f);
        }
        if (yRay.direction().getY() > 0) {
            corner.additionInPlace(yRay.direction().scalarMultiply((float) height));
            yRay.direction().scalarMultiplyInPlace(-1f);
        }
    }

    private void rotateRectangle(float theta, float phi, Vector phiRotationAxis) {
        // rotate normal, corner, and rays defining rectangle into xy plane
        normal.rotationInPlace(theta, phi, phiRotationAxis);
        xRay.direction().rotationInPlace(theta, phi, phiRotationAxis);

        yRay.direction().rotationInPlace(theta, phi, phiRotationAxis);
        corner.rotationInPlace(theta, phi, phiRotationAxis);
    }

    public String toString() {
        return "Rectangle with corner: (" + corner.getX() + ", " + corner.getY() + ", " + corner.getZ() + ") " +
                ", x direction: (" + xRay.direction().getX() + ", " + xRay.direction().getY() + ", " + xRay.direction().getZ() + ") " +
                ", y direction: (" + yRay.direction().getX() + ", " + yRay.direction().getY() + ", " + yRay.direction().getZ() + ") " +
                ", normal: (" + normal.getX() + ", " + normal.getY() + ", " + normal.getZ() + ") " +
                ", WidthxHeight: " + width + "x" + height;
    }

    private void setXRayToLongestAndYRayToShortestSide() {
        Vector edge = point.subtract(corner);
        Vector otherEdge = otherPoint.subtract(corner);
        if (edge.getModulus() >= otherEdge.getModulus()) {
            xRay = new Line(corner, point).parametric();
            width = Math.round(edge.getModulus());

            yRay = new Line(corner, otherPoint).parametric();
            height = Math.round(otherEdge.getModulus());

        } else {
            yRay = new Line(corner, point).parametric();
            height = Math.round(edge.getModulus());

            xRay = new Line(corner, otherPoint).parametric();
            width = Math.round(otherEdge.getModulus());
        }
    }

    private void findCornerAndSetPoints(Vector[] vertices) {
        float dotEdgeCombo1 = vertices[1].subtract(vertices[0]).dotProduct(vertices[2].subtract(vertices[0]));
        float dotEdgeCombo2 = vertices[0].subtract(vertices[1]).dotProduct(vertices[2].subtract(vertices[1]));
        if (Math.abs(dotEdgeCombo1) < EPSILON) {
            corner = vertices[0];
        } else if (Math.abs(dotEdgeCombo2) < EPSILON) {
            corner = vertices[1];
        } else {
            corner = vertices[2];
        }
        ArrayList<Vector> verticesList = new ArrayList<>(Arrays.asList(vertices));
        verticesList.remove(corner);
        point = verticesList.remove(0);
        otherPoint = verticesList.remove(0);
    }

    public Vector getNormal() {
        return normal;
    }

    public Vector getNormal(Vector intersectionPoint) {
        return getNormal();
    }

    @Override
    public Vector getOrigin() {
        return corner;
    }

    @Override
    public boolean inside(Vector point) {
        return false;
    }

    @Override
    public float getDiffuseCoefficient() {
        return 0.8f;
    }


}
