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
}
