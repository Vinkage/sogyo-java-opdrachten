package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

import com.aparapi.Kernel;
import com.aparapi.Range;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape;


public class GpuAcceleratedScene extends Scene {

    private Kernel kernel;

    public GpuAcceleratedScene(Vector viewpoint, Viewport viewport, Lightsource[] lightsources, Shape[] shapes, Colors colors) {
        super(viewpoint, viewport, lightsources, shapes, colors);
    }

    public GpuAcceleratedScene(Vector viewpoint, Viewport viewport, Lightsource[] lightsources, Shape[] shapes) {
        super(viewpoint, viewport, lightsources, shapes);
    }

    public void toJpg() {
        draw();
        writeImage();
    }

    private void draw() {
        int width = viewport.getWidth();
        int height = viewport.getHeight();

        int[] in = new int[width * height];
        int[] in2 = new int[width * height];

        for (int i = 0; i < in.length; i++) {
            in[i] = 1;
            in[2] = 2;
        }

        int[] out = new int[width * height];

        kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId();
                out[i] = in[i] + in2[i];
            }
        };

        Range range = Range.create(out.length);
        kernel.execute(range);
        System.out.println("Exec mode: " + kernel.getAccumulatedExecutionTime());

        // for (int i = 0; i < viewport.getWidth(); i++) {
        //     for (int j = 0; j < viewport.getHeight(); j++) {
        //         Vector pixel = viewport.getVector(new Coordinate(i,j));

        //         float brightness = calculatePixelBrightness(pixel);
        //         brightnessValueToImage(i, j, brightness);
        //     }
        // }
    }

}
