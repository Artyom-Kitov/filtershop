package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class SepiaTool implements Tool {

    private static final double[] RED_FACTORS = { 0.393, 0.769, 0.189 };
    private static final double[] GREEN_FACTORS = { 0.349, 0.686, 0.168 };
    private static final double[] BLUE_FACTORS = { 0.272, 0.534, 0.131 };

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int color = original.getRGB(x, y);
                int r = ColorUtils.getRed(color);
                int g = ColorUtils.getGreen(color);
                int b = ColorUtils.getBlue(color);
                int newR = (int) (r * RED_FACTORS[0] + g * RED_FACTORS[1] + b * RED_FACTORS[2]);
                int newG = (int) (r * GREEN_FACTORS[0] + g * GREEN_FACTORS[1] + b * GREEN_FACTORS[2]);
                int newB = (int) (r * BLUE_FACTORS[0] + g * BLUE_FACTORS[1] + b * BLUE_FACTORS[2]);
                result.setRGB(x, y, ColorUtils.getRGB(newR, newG, newB));
            }
        }
    }

}
