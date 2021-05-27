package nl.sogyo.javaopdrachten.raytracer;

public class Sphere implements Shape {
    private Vector origin;
    private float radius;

    public Sphere(Vector origin, float radius) {
        this.origin = origin;
        this.radius = radius;
    }

    @Override
    public Vector[] intersect(Line line) {
        // https://www.youtube.com/watch?v=HFPlKQGChpE&ab_channel=TheArtofCode
        return new Vector[0];
    }
}
