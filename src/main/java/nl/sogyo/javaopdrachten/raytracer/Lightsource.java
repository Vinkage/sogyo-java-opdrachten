package nl.sogyo.javaopdrachten.raytracer;

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
}
