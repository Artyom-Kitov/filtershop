package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class InversionTool implements Tool {

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int r = 255 - ColorUtils.getRed(original, x, y);
                int g = 255 - ColorUtils.getGreen(original, x, y);
                int b = 255 - ColorUtils.getBlue(original, x, y);
                result.setRGB(x, y, ColorUtils.getRGB(r, g, b));
            }
        }
    }

}
