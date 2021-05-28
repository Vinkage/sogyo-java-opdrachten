package nl.sogyo.javaopdrachten.raytracer;

import java.util.ArrayList;

public class Scene {
    private Vector viewpoint;
    private Viewport viewport;
    private ArrayList<Lightsource> myLightsources = new ArrayList<>();
    private ArrayList<Shape> myShapes = new ArrayList<>();

    public Scene(Vector viewpoint, Viewport viewport, Lightsource[] lightsources, Shape[] shapes) {
        this.viewpoint = viewpoint;
        this.viewport = viewport;
        for (Lightsource lightsource: lightsources)
            myLightsources.add(lightsource);
        for (Shape shape: shapes)
            myShapes.add(shape);
    }

    public String toString() {
        String shapes = "";
        for (Shape shape: this.myShapes)
            shapes = shapes + shape.toString() + "\n";

        String lightsources = "";
        for (Lightsource lightsource: this.myLightsources)
            lightsources = lightsources + lightsource.toString() + "\n";
        return "viewpoint: " + viewpoint +
                "\nviewport: " + viewport + "\n" +
                shapes +
                lightsources;
    }

    public float[][] getProjection() {
        float[][] pixelIntensities = new float[viewport.getWidth()][viewport.getHeight()];

        for (int i = 0; i < viewport.getWidth(); i++) {
            for (int j = 0; j < viewport.getHeight(); j++) {
                Vector pixel3DLocation = viewport.getVector(new Coordinate(i,j));
                Line ray = new Line(viewpoint, pixel3DLocation);

                Vector nearestIntersection = nearestInterection(viewpoint, ray);
                if (nearestIntersection != null) {
                    pixelIntensities[i][j] = getIntensity(nearestIntersection);
                } else {
                    pixelIntensities[i][j] = 0f;
                }

            }
        }
        return pixelIntensities;
    }

    private float getIntensity(Vector intersection) {
        float intensity = 0f;
        for (Lightsource lightsource: myLightsources) {
            Line fromIntersectionToLightSource = new Line(intersection, lightsource.getPosition());
            for (Shape shape: myShapes) {
                Vector[] obstructions;

                Vector nearestObstruction = nearestInterection(intersection, fromIntersectionToLightSource);
                if (nearestObstruction != null) {
                    if (nearestObstruction.subtraction(intersection).getModulus() > lightsource.getPosition().subtraction(intersection).getModulus()) {
                        intensity = intensity + lightsource.getBrightness();
                    }
                } else {
                    intensity = intensity + lightsource.getBrightness();
                }
            }
        }
        return intensity;
    }

    private Vector nearestInterection(Vector reference, Line ray) {
        Vector nearestIntersection = null;

        for (Shape shape : myShapes) {
            Vector[] intersections;
            try {
                intersections = shape.intersect(ray);
            } catch (NoIntersectionPossible e) {
                intersections = null;
            }

            if (intersections != null) {
                for (Vector intersection : intersections) {
                    if (nearestIntersection == null) {
                        nearestIntersection = intersection;
                    } else if (nearestIntersection.subtraction(reference).getModulus() > intersection.subtraction(reference).getModulus()) {
                        nearestIntersection = intersection;
                    }
                }
            }
        }

        return nearestIntersection;

    }
}
