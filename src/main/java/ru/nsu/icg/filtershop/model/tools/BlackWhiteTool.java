package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class BlackWhiteTool implements Tool {

    private static final double[] FACTORS = { 0.299, 0.587, 0.114 };

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        int width = original.getWidth();
        int height = original.getHeight();
        int[] pixels = original.getRGB(0, 0, width, height, null, 0, width);
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int c = pixels[y * width + x];
                int r = ColorUtils.getRed(c);
                int g = ColorUtils.getGreen(c);
                int b = ColorUtils.getBlue(c);
                int gray = (int) (r * FACTORS[0] + g * FACTORS[1] + b * FACTORS[2]);
                result.setRGB(x, y, ColorUtils.getRGB(gray, gray, gray));
            }
        }
    }

}
