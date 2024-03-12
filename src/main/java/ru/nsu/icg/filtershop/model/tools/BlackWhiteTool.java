package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class BlackWhiteTool implements Tool {

    private static final double[] FACTORS = {0.299, 0.587, 0.114};

    @Override
    public void applyTo(BufferedImage original, BufferedImage edited) {
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int color = original.getRGB(x, y);
                int r = ColorUtils.getRed(color);
                int g = ColorUtils.getGreen(color);
                int b = ColorUtils.getBlue(color);
                int gray = (int) (r * FACTORS[0] + g * FACTORS[1] + b * FACTORS[2]);
                edited.setRGB(x, y, (gray << 16) + (gray << 8) + gray);
            }
        }
    }

}
