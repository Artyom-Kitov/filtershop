package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class BlackWhiteTool implements Tool {

    private static final double[] FACTORS = { 0.299, 0.587, 0.114 };

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int r = ColorUtils.getRed(original, x, y);
                int g = ColorUtils.getGreen(original, x, y);
                int b = ColorUtils.getBlue(original, x, y);
                int gray = (int) (r * FACTORS[0] + g * FACTORS[1] + b * FACTORS[2]);
                result.setRGB(x, y, ColorUtils.getRGB(gray, gray, gray));
            }
        }
    }

}
