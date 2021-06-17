package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

public class Lightsource {
    private int brightness;
    private Vector position;

    public Lightsource(int brightness, Vector position) {
        this.brightness = brightness;
        this.position = position;
    }

    public String toString() {
        return "lightsource: " + position.toString() + " - " +
                "Brightness: " + brightness;
    }

    public Vector getPosition() {
        return position;
    }

    public float getBrightness() {
        return brightness;
    }
}
