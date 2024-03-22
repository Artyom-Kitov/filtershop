package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class SepiaTool implements Tool {

    private static final double[] RED_FACTORS = { 0.393, 0.349, 0.272 };
    private static final double[] GREEN_FACTORS = { 0.769, 0.686, 0.534 };
    private static final double[] BLUE_FACTORS = { 0.189, 0.168, 0.131 };

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        int width = original.getWidth();
        int height = original.getHeight();
        int[] pixels = original.getRGB(0, 0, width, height, null, 0, width);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = pixels[y * width + x];
                int r = ColorUtils.getRed(color);
                int g = ColorUtils.getGreen(color);
                int b = ColorUtils.getBlue(color);

                int newR = (int) (r * RED_FACTORS[0] + g * GREEN_FACTORS[0] + b * BLUE_FACTORS[0]);
                int newG = (int) (r * RED_FACTORS[1] + g * GREEN_FACTORS[1] + b * BLUE_FACTORS[1]);
                int newB = (int) (r * RED_FACTORS[2] + g * GREEN_FACTORS[2] + b * BLUE_FACTORS[2]);

                if (newR > 255) newR = 255;
                if (newG > 255) newG = 255;
                if (newB > 255) newB = 255;
                result.setRGB(x, y, ColorUtils.getRGB(newR, newG, newB));
            }
        }
    }

}
