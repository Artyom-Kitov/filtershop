package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.RGBMatrix;
import ru.nsu.icg.filtershop.model.ColorUtils;

import java.awt.image.BufferedImage;

public class InversionTool implements Tool {

    @Override
    public void applyTo(RGBMatrix matrix) {
        BufferedImage resized = matrix.getResized();
        BufferedImage edited = matrix.getEdited();
        for (int y = 0; y < resized.getHeight(); y++) {
            for (int x = 0; x < resized.getWidth(); x++) {
                int color = resized.getRGB(x, y);
                int r = 255 - ColorUtils.getRed(color);
                int g = 255 - ColorUtils.getGreen(color);
                int b = 255 - ColorUtils.getBlue(color);
                edited.setRGB(x, y, (r << 16) + (g << 8) + b);
            }
        }
    }

}
