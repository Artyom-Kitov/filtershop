package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.RGBMatrix;

import java.awt.image.BufferedImage;

public class InversionTool implements Tool {

    @Override
    public void applyTo(RGBMatrix matrix) {
        BufferedImage resized = matrix.getResized();
        BufferedImage edited = matrix.getEdited();
        for (int y = 0; y < resized.getHeight(); y++) {
            for (int x = 0; x < resized.getWidth(); x++) {
                int color = resized.getRGB(x, y);
                int r = (color & 0xff0000) >> 16;
                int g = (color & 0x00ff00) >> 8;
                int b = color & 0x0000ff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                edited.setRGB(x, y, (r << 16) + (g << 8) + b);
            }
        }
    }

}
