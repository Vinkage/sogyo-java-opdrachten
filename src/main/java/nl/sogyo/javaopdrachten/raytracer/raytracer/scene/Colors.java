package nl.sogyo.javaopdrachten.raytracer.raytracer.scene;

import java.awt.*;

public class Colors {
    private String mapping;

    public Colors(String mappingName) {
        mapping = mappingName;
    }

    public String getMapping() { return mapping;}

    public Colors() {
        mapping = "grayscale";
    }
    public Color mapGrayScaleToColor(int pixel) {
        switch (mapping) {
            case "grayscale":
                return grayscale(pixel);
            case "rainbow":
                return rainbow(pixel);
            case "pseudocolor":
                return pseudocolor(pixel);
            default:
                return grayscale(pixel);
        }
    }

    private Color pseudocolor(int pixel) {
        return new Color(Math.abs(255 - pixel), Math.abs(127 - pixel), Math.abs(0 - pixel));
    }

    private Color rainbow(int pixelBrightness) {
        if (pixelBrightness <= 51) {
            return new Color(255, pixelBrightness * 5, 0);
        } else if (pixelBrightness <= 102) {
            pixelBrightness -= 51;
            return new Color(255 - pixelBrightness * 5, 255, 0);
        } else if (pixelBrightness <= 153) {
            pixelBrightness -= 102;
            return new Color(0, 255, pixelBrightness * 5);
        } else if (pixelBrightness <= 204) {
            pixelBrightness -= 153;
            return new Color(0, (int) (255 - (pixelBrightness * 128.0 / 51 + 0.5)), 255);
        } else if (pixelBrightness <= 255) {
            pixelBrightness -= 204;
            return new Color(0, (int) (127 - (pixelBrightness * 127.0 / 51 + 0.5)), 255);
        }
        return null;
    }

    private Color grayscale(int pixelBrigthness) {
        return new Color(pixelBrigthness, pixelBrigthness, pixelBrigthness);
    }
}
