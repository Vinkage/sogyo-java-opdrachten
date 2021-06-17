package nl.sogyo.javaopdrachten.raytracer;

import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Lightsource;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Scene;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Vector;
import nl.sogyo.javaopdrachten.raytracer.raytracer.scene.Viewport;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Rectangle;
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

        // Scene academyExample = new Scene(
        //         new Vector(0, 0, 0),
        //         new Viewport(deepCopyVectorArray(viewportVertices)),
        //         new Lightsource[]{
        //                 new Lightsource(50, new Vector(500, 500, 100)),
        //                 new Lightsource(50, new Vector(500, -100, 75)),
        //                 new Lightsource(50, new Vector(0, 0, 0)),
        //         },
        //         new Shape[]{
        //                 new Sphere(new Vector(0, 0, 100), 200),
        //                 new Sphere(new Vector(100, 150, 130), 50),
        //         }
        // );
        // scenes.put("academyExample.jpg",academyExample);

        // Scene normalSphere = new Scene(
        //         new Vector(0, 0, 0),
        //         new Viewport(deepCopyVectorArray(viewportVertices)),
        //         new Lightsource[]{
        //                 new Lightsource(50, new Vector(500, 500, 100)),
        //                 new Lightsource(50, new Vector(500, -100, 75)),
        //                 new Lightsource(50, new Vector(0, 0, 0)),
        //         },
        //         new Shape[]{
        //                 new Sphere(new Vector(0, 0, 160), 100),
        //         }
        // );
        // scenes.put("normalSphere.jpg",normalSphere);

        // Scene intersectingSpheres = new Scene(
        //         new Vector(0, 0, 0),
        //         new Viewport(deepCopyVectorArray(viewportVertices)),
        //         new Lightsource[] {
        //                 new Lightsource(50, new Vector(0, 0, 0)),
        //                 // new Lightsource(50, new Vector(500, 500, 100)),
        //                 // new Lightsource(50, new Vector(500, -100, 75)),

        //         },
        //         new Shape[] {

        //                 new Sphere(new Vector(-50, 0, 150), 100),
        //                 new Sphere(new Vector(50, 0, 150), 100),
        //         }
        // );
        // scenes.put("intersectingSpheres.jpg",intersectingSpheres);

        // Scene ballAndLightInside = new Scene(
        //         new Vector(0, 0, 0),
        //         new Viewport(deepCopyVectorArray(viewportVertices)),
        //         new Lightsource[] {
        //                 new Lightsource(50, new Vector(0, 0, 0)),
        //                 new Lightsource(50, new Vector(500, 500, 100)),
        //                 new Lightsource(50, new Vector(500, -100, 75)),
        //                 new Lightsource(50, new Vector(0, 0, 180)),
        //                 new Lightsource(50, new Vector(0, -50, 180)),

        //         },
        //         new Shape[] {
        //                 new Sphere(new Vector(-50, 0, 240), 200),
        //                 new Sphere(new Vector(-60, 0, 230), 40),
        //         }
        // );
        // scenes.put("ballAndLightInsideOtherBall.jpg",ballAndLightInside);

        // Scene pumpkin = new Scene(
        //         new Vector(0, 0, 0),
        //         new Viewport(deepCopyVectorArray(viewportVertices)),
        //         new Lightsource[]{
        //                 new Lightsource(50, new Vector(0, 0, 0)),
        //                 new Lightsource(50, new Vector(0, 0, 100)),
        //                 new Lightsource(50, new Vector(500, 500, 100)),
        //                 new Lightsource(50, new Vector(500, -100, 75)),

        //         },
        //         new Shape[]{
        //                 new Sphere(new Vector(0, 0, 100), 2000),
        //                 new Sphere(new Vector(50, 0, 100), 200),
        //                 new Sphere(new Vector(-50, 0, 100), 200),
        //                 new Sphere(new Vector(50, 0, 100), 20),
        //                 new Sphere(new Vector(-50, 0, 100), 20),
        //         }
        // );
        // scenes.put("pumpkin.jpg",pumpkin);

        Scene ballInRoom = new Scene(
                new Vector(0, 0, -1000),
                new Viewport(deepCopyVectorArray(viewportVertices)),
                new Lightsource[] {
                        new Lightsource(200, new Vector(400, 300, 300)),

                },
                new Shape[] {
                        new Rectangle(new Vector[] {
                                new Vector(-400, -300, 60),
                                new Vector(-400, -300, 1000),
                                new Vector(400, -300, 1000),
                        }),
                        new Rectangle(new Vector[] {
                                new Vector(-400, -300, 1000),
                                new Vector(400, -300, 1000),
                                new Vector(400, 300, 1000),
                        }),
                        new Rectangle(new Vector[] {
                                new Vector(-400, -300, 60),
                                new Vector(-400, -300, 1000),
                                new Vector(-400, 300, 1000),
                        }),
                        new Sphere(new Vector(0, 100, 500), 60),
                }
        );
        scenes.put("ballInRoom.jpg",ballInRoom);
    }

    public static void main(String[] args) {
        ExampleScenes exampleScenes = new ExampleScenes();

        exampleScenes.draw("examples");
    }

    public void draw(String directory) {
        for (Map.Entry<String, Scene> scene: scenes.entrySet()) {
            String fileName = scene.getKey();
            Scene exampleScene = scene.getValue();
            exampleScene.toJpg(directory, fileName);
        }
    }

    private Vector[] deepCopyVectorArray(Vector[] array) {
        Vector[] copy = new Vector[array.length];
        for (int i = 0; i < array.length; i++) {
            copy[i] = new Vector(array[i]);
        }
        return copy;
    }
}
