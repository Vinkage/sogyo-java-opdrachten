package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

import nl.sogyo.javaopdrachten.raytracer.raytracer.anglecalculator.AngleCalculator;
import nl.sogyo.javaopdrachten.raytracer.raytracer.exceptions.NoIntersectionPossible;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Line;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Scene {
    private Vector viewpoint;
    private Viewport viewport;
    private ArrayList<Lightsource> myLightsources = new ArrayList<>();
    private ArrayList<nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape> myShapes = new ArrayList<>();

    private AngleCalculator angleCalculator = new AngleCalculator();

    private float maxBrightness;
    private final BufferedImage image;
    private static final double EPSILON = 1e-5;

    public Scene(Vector viewpoint, Viewport viewport, Lightsource[] lightsources, nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape[] shapes) {
        this.viewpoint = viewpoint;
        this.viewport = viewport;
        for (Lightsource lightsource: lightsources)
            myLightsources.add(lightsource);
        for (nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape shape: shapes)
            myShapes.add(shape);

        maxBrightness = 0;
        for (Lightsource lightsource: lightsources) maxBrightness = maxBrightness + lightsource.getBrightness();

        System.out.println(viewport);
        image =  new BufferedImage(viewport.getWidth(), viewport.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);

    }

    public String toString() {
        String shapes = "";
        for (nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape shape: this.myShapes)
            shapes = shapes + shape.toString() + "\n";

        String lightsources = "";
        for (Lightsource lightsource: this.myLightsources)
            lightsources = lightsources + lightsource.toString() + "\n";
        return "viewpoint: " + viewpoint +
                "\nviewport: " + viewport + "\n" +
                shapes +
                lightsources;
    }

    public void toJpg() {
        draw();
        writeImage();
    }

    public void toJpg(String fileName) {
        draw();
        writeImage(fileName);
    }
    public void toJpg(String directory, String fileName) {
        draw();
        writeImage(directory, fileName);
    }

    private void draw() {
        for (int i = 0; i < viewport.getWidth(); i++) {
            for (int j = 0; j < viewport.getHeight(); j++) {
                Vector pixel = viewport.getVector(new Coordinate(i,j));

                float brightness = calculatePixelBrightness(pixel);
                brightnessValueToImage(i, j, brightness);
            }
        }
    }

    private float calculatePixelBrightness(Vector pixel) {
        // make a line
        Line line = new Line(viewpoint, pixel);
        float distanceFromViewpointToPixel = pixel.subtract(viewpoint).getModulus();

        // Get nearest intersection along line, after viewport
        Intersection intersection = nearestIntersection(line, distanceFromViewpointToPixel);

        if (!(intersection == null)) {
            return brightnessOfReflection(intersection, line);
        }
        return 0f;
    }

    private float brightnessOfReflection(Intersection intersection, Line lineFromViewpoint) {
        float angleOfIntersectionWithShape = intersection.getAngleOfIntersection();
        Vector intersectionPoint = intersection.getPoint();
        float brightness = 0f;

        for (Lightsource lightsource: myLightsources) {
            Line lineToLight = new Line(intersectionPoint, lightsource.getPosition());

            if (angleOfIntersectionWithShape > Math.PI / 2 && angleOfIntersectionWithShape < Math.PI * 3 / 2) {
                brightness = brightness + reflectionOnTheOutsideOfShape(intersection, lightsource, lineToLight);
            } else {
                brightness = brightness + reflectionFromTheInsideOfShape(intersection, lightsource, lineToLight);
            }
        }

        return brightness;
    }

    private float reflectionFromTheInsideOfShape(Intersection reflection, Lightsource lightsource, Line lineToLight) {
        Vector reflectionNormal = reflection.getNormal();
        Vector lineToLightDirection = lineToLight.parametric().direction();

        boolean impossibleReflection = lineToLightDirection.dotProduct(reflectionNormal) > 0;
        if (impossibleReflection) return 0;

        if (lightSourceIsOutsideAndWeAreNotLookingIntoShapeFromOutside(reflection, lineToLight, lightsource)) return 0;

        for (Shape shape: myShapes) {

            if (shape == reflection.getShape()) continue;

            if (reflectionCannotReachSource(reflection, lineToLight, shape, lightsource)) return 0;

        }

        return lightsource.getBrightness();
    }

    private boolean lightSourceIsOutsideAndWeAreNotLookingIntoShapeFromOutside(Intersection reflection, Line lineToLight, Lightsource lightsource) {
        try {
            ArrayList<Vector> points = new ArrayList<>(Arrays.asList(reflection.getShape().intersect(lineToLight)));
            removeReflectionPointItself(reflection, points);

            for (Vector potentialBlockingPoint: points) {

                boolean lightSourceOutside = potentialBlockingPoint.subtract(reflection.getPoint()).getModulus() < lightsource.getPosition().subtract(reflection.getPoint()).getModulus();
                boolean pointIsInRightDirection = potentialBlockingPoint.subtract(reflection.getPoint()).dotProduct(lineToLight.parametric().direction()) > 0;
                boolean outside = lightSourceOutside && pointIsInRightDirection;

                boolean betweenViewPortAndViewpoint = betweenViewportAndViewpoint(lineToLight, potentialBlockingPoint);

                if (outside && betweenViewPortAndViewpoint) {
                    continue;
                }
                if (outside) {
                    return true;
                }
            }
        } catch (NoIntersectionPossible noIntersectionPossible) {
            return true;
        }
        return false;
    }

    private boolean betweenViewportAndViewpoint(Line lineToLight, Vector point) {
        boolean betweenViewPortAndViewpoint = false;
        try {
            Vector viewportIntersectCheck = viewport.intersect(lineToLight)[0];
            Vector viewportNormal = viewport.getNormal();

            Vector toPoint = point.subtract(viewpoint);
            float toPointNormalToViewport = toPoint.dotProduct(viewportNormal);

            float viewPortViewPointDistance = viewport.getOrigin().subtract(viewpoint).dotProduct(viewportNormal);


            betweenViewPortAndViewpoint = toPointNormalToViewport < viewPortViewPointDistance; // && notTooBig;
        } catch (NoIntersectionPossible e) {

        }
        return betweenViewPortAndViewpoint;
    }

    private ArrayList<Vector> removeReflectionPointItself(Intersection intersection, ArrayList<Vector> points) {
        Vector pointOfInterest = intersection.getPoint();
        Vector nearest = null;
        for (Vector point: points) {
            if (nearest == null) {
                nearest = point;
            }
            if (point.subtract(pointOfInterest).getModulus() < nearest.subtract(pointOfInterest).getModulus()) {
                nearest = point;
            }
        }
        points.remove(nearest);
        return points;
    }

    private float reflectionOnTheOutsideOfShape(Intersection reflection, Lightsource lightsource, Line lineToLight) {
        Vector intersectionNormal = reflection.getNormal();
        Vector lineToLightDirection = lineToLight.parametric().direction();

        boolean impossibleReflection = lineToLightDirection.dotProduct(intersectionNormal) < 0;
        if (impossibleReflection) return 0;

        for (Shape shape: myShapes) {

            if (shape == reflection.getShape()) continue;

            if (reflectionCannotReachSource(reflection, lineToLight, shape, lightsource)) return 0;

        }
        return lightsource.getBrightness();
    }

    private boolean reflectionCannotReachSource(Intersection reflection, Line lineToLight, Shape shape, Lightsource lightsource) {
        try {

            Vector[] points = shape.intersect(lineToLight);
            for (Vector potentialBlockingPoint: points) {
                boolean pointIsToofar = potentialBlockingPoint.subtract(reflection.getPoint()).getModulus() > lightsource.getPosition().subtract(reflection.getPoint()).getModulus();
                boolean pointIsInRightDirection = potentialBlockingPoint.subtract(reflection.getPoint()).dotProduct(lineToLight.parametric().direction()) > 0;
                boolean doesntblock = pointIsToofar && pointIsInRightDirection;

                boolean betweenViewPortAndViewpoint = betweenViewportAndViewpoint(lineToLight, potentialBlockingPoint);

                if (doesntblock) continue;
                else if (!pointIsInRightDirection) continue;
                else if (betweenViewPortAndViewpoint) continue;
                return true;
            }
            return false;

        } catch (NoIntersectionPossible e) {
            return false;
        }
    }

    private Intersection nearestIntersection(Line line, float distance) {
        Intersection intersection = null;
        Intersection nearestIntersection = null;

        for (Shape shape: myShapes) {
            Vector[] intersectionPoints;
            try {
                intersectionPoints = shape.intersect(line);
            } catch (NoIntersectionPossible e) {
                continue;
            }

            for (Vector point: intersectionPoints) {


                boolean linePointsTowardsIntersSection = line.parametric().direction().dotProduct(point.subtract(viewpoint)) > 0;
                boolean pastViewPort = point.subtract(viewpoint).getModulus() > distance;
                if (linePointsTowardsIntersSection && pastViewPort) {
                    intersection = new Intersection(
                            point,
                            shape.calculateAngle(line, point),
                            line,
                            shape
                    );


                    if (nearestIntersection == null) {
                        nearestIntersection = intersection;
                        continue;
                    }

                    if (nearestIntersection.getPoint().subtract(viewpoint).getModulus() < point.subtract(viewpoint).getModulus()) {
                    } else {
                        nearestIntersection = intersection;
                    }
                }
            }
        }

        return nearestIntersection;
    }

    private void brightnessValueToImage(int row, int col, float brightness) {
        int brightnessAdjustedPixel;
        if (brightness != 0)
            brightnessAdjustedPixel = (int) ((brightness / maxBrightness) * 255);
        else
            brightnessAdjustedPixel = 0;

        Color color = new Color(brightnessAdjustedPixel, brightnessAdjustedPixel, brightnessAdjustedPixel);
        image.setRGB(row, col, color.getRGB());
        }

    public void writeImage() {
        writeImage("Grayscale.jpg");
    }

    public void writeImage(String directory, String fileName) {
        writeImage(directory + "/" + fileName);
    }

    public void writeImage(String fileName) {
        File output = new File(fileName);
        try {
            ImageIO.write(image, "jpg", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Viewport getViewport() {
        return viewport;
    }
}
