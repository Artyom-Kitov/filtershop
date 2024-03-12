package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class InversionTool implements Tool {

    @Override
    public void applyTo(BufferedImage original, BufferedImage edited) {
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int color = original.getRGB(x, y);
                int r = 255 - ColorUtils.getRed(color);
                int g = 255 - ColorUtils.getGreen(color);
                int b = 255 - ColorUtils.getBlue(color);
                edited.setRGB(x, y, (r << 16) + (g << 8) + b);
            }
        }
    }

}
