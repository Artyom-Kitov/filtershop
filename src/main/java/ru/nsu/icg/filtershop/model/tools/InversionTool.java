package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class InversionTool implements Tool {

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        int width = original.getWidth();
        int height = original.getHeight();
        int[] pixels = original.getRGB(0, 0, width, height, null, 0, width);
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int color = pixels[y * width + x];
                int r = 255 - ColorUtils.getRed(color);
                int g = 255 - ColorUtils.getGreen(color);
                int b = 255 - ColorUtils.getBlue(color);
                result.setRGB(x, y, ColorUtils.getRGB(r, g, b));
            }
        }
    }

}
