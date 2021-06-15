package nl.sogyo.javaopdrachten.raytracer;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Lightsource;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Scene;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Viewport;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Sphere;

import java.util.Hashtable;
import java.util.Map;

public class ExampleScenes {
    private Hashtable<String, Scene> scenes = new Hashtable();

    {
        Vector[] viewportVertices = new Vector[]{
                new Vector(-400, 300, 50),
                new Vector(400, 300, 50),
                new Vector(400, -300, 50),
        };

        Scene academyExample = new Scene(
                new Vector(0, 0, 0),
                new Viewport(viewportVertices),
                new Lightsource[]{
                        new Lightsource(50, new Vector(500, 500, 100)),
                        new Lightsource(50, new Vector(500, -100, 75)),
                },
                new Shape[]{
                        new Sphere(new Vector(0, 0, 100), 200),
                        new Sphere(new Vector(100, 150, 130), 50),
                }
        );
        scenes.put("academyExample.jpg",academyExample);

        Scene normalSphere = new Scene(
                new Vector(0, 0, 0),
                new Viewport(viewportVertices),
                new Lightsource[]{
                        new Lightsource(50, new Vector(500, 500, 100)),
                        new Lightsource(50, new Vector(500, -100, 75)),
                },
                new Shape[]{
                        new Sphere(new Vector(0, 0, 100), 50),
                }
        );
        scenes.put("normalSphere.jpg",normalSphere);

        Scene pumpkin = new Scene(
                new Vector(0, 0, 0),
                new Viewport(viewportVertices),
                new Lightsource[]{
                        new Lightsource(50, new Vector(0, 0, 0)),
                        new Lightsource(50, new Vector(0, 0, 100)),
                        new Lightsource(50, new Vector(500, 500, 100)),
                        new Lightsource(50, new Vector(500, -100, 75)),

                },
                new Shape[]{
                        new Sphere(new Vector(0, 0, 100), 2000),
                        new Sphere(new Vector(50, 0, 100), 200),
                        new Sphere(new Vector(-50, 0, 100), 200),
                        new Sphere(new Vector(50, 0, 100), 20),
                        new Sphere(new Vector(-50, 0, 100), 20),
                }
        );
        scenes.put("pumpkin.jpg",pumpkin);
    }

    public static void main(String[] args) {
        ExampleScenes exampleScenes = new ExampleScenes();

        exampleScenes.draw("examples");
    }

    public void draw(String directory) {
        for (Map.Entry<String, Scene> scene: scenes.entrySet()) {
            String fileName = scene.getKey();
            Scene exampleScene = scene.getValue();
            scene.draw(directory, fileName);
        }
    }
}
