package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Scene {
    private Vector viewpoint;
    private Viewport viewport;
    private ArrayList<Lightsource> myLightsources = new ArrayList<>();
    private ArrayList<nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape> myShapes = new ArrayList<>();

    private float maxBrightness;
    private final BufferedImage image;

    public Scene(Vector viewpoint, Viewport viewport, Lightsource[] lightsources, nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape[] shapes) {
        this.viewpoint = viewpoint;
        this.viewport = viewport;
        for (Lightsource lightsource: lightsources)
            myLightsources.add(lightsource);
        for (nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape shape: shapes)
            myShapes.add(shape);

        maxBrightness = 0;
        for (Lightsource lightsource: lightsources) maxBrightness = maxBrightness + lightsource.getBrightness();

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

    public void draw() {
        for (int i = 0; i < viewport.getWidth(); i++) {
            for (int j = 0; j < viewport.getHeight(); j++) {
                Vector pixel = viewport.getVector(new Coordinate(i,j));

            }
        }
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
        File output = new File("Grayscale.jpg");
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
