package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;


import com.aparapi.Kernel;
import com.aparapi.Range;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Shape;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Rectangle;
import nl.sogyo.javaopdrachten.raytracer.raytracer.shapes.Sphere;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.nio.IntBuffer;


public class GpuAcceleratedScene extends Scene {



    public static class rayTracerKernel extends Kernel {
        private final Range range;

        private final float[] viewpointXyz;

        private final int[] pixels;

        private final BufferedImage image;

        public rayTracerKernel(Range _range, Scene scene) {
            range = _range;
            viewpointXyz = scene.viewpoint.getCartesianPrimitives();
            scene.draw();
            pixels = scene.pixelBrightness;
            image = scene.image;
        }

        @Override
        public void run() {
            //TODO
        }

        protected void render(GL2 gl) {

            Buffer buffer = IntBuffer.wrap(pixels);

            gl.glDrawPixels(
                    width,
                    height,
                    gl.GL_RGBA,
                    gl.GL_UNSIGNED_BYTE,
                    buffer
            );
        }

    }

    static BufferedImage image;
    static int width;
    static int height;

    public static void main(String[] args) {
        Vector[] viewportVertices = new Vector[] {
                new Vector(-400, 300, 0),
                new Vector(400, 300, 0),
                new Vector(400, -300, 0),
        };
        GpuAcceleratedScene initialScene = new GpuAcceleratedScene(
                new Vector(0, 0, -1000),
                new Viewport(viewportVertices),
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
                },
                new Colors("pseudocolor")
        );

        width = initialScene.getViewport().getWidth();
        height = initialScene.getViewport().getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);

        rayTracerKernel rayTracerKernel = new rayTracerKernel(Range.create(width * height), initialScene);

        final JFrame frame = new JFrame("raytracer");

        final JPanel panel = new JPanel(new BorderLayout());

        final JPanel controlPanel = new JPanel(new FlowLayout());

        panel.add(controlPanel, BorderLayout.NORTH);

        final JButton drawButton = new JButton("draw");

        final GLCapabilities caps = new GLCapabilities(null);
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);
        final GLCanvas canvas = new GLCanvas(caps);

        final Dimension dimension = new Dimension(Integer.getInteger("width", 742 - 64), Integer.getInteger("height", 742 - 64));
        canvas.setPreferredSize(dimension);


        canvas.addGLEventListener(new GLEventListener() {

            @Override
            public void init(GLAutoDrawable glAutoDrawable) {

            }

            @Override
            public void dispose(GLAutoDrawable glAutoDrawable) {

            }

            @Override
            public void display(GLAutoDrawable glAutoDrawable) {
                final GL2 gl = glAutoDrawable.getGL().getGL2();

                rayTracerKernel.render(gl);
            }

            @Override
            public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

            }
        });

        rayTracerKernel.execute(rayTracerKernel.range);


        panel.add(canvas, BorderLayout.CENTER);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        // FPSAnimator animator = new FPSAnimator(canvas, 10);
        // animator.start();
    }

    public GpuAcceleratedScene(Vector viewpoint, Viewport viewport, Lightsource[] lightsources, Shape[] shapes, Colors colors) {
        super(viewpoint, viewport, lightsources, shapes, colors);
    }

    public GpuAcceleratedScene(Vector viewpoint, Viewport viewport, Lightsource[] lightsources, Shape[] shapes) {
        super(viewpoint, viewport, lightsources, shapes);
    }

    public void toJpg() {
        // draw();
        writeImage();
    }

}
