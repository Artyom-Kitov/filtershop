package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.RGBMatrix;

import java.awt.image.BufferedImage;

public class BlackWhiteTool implements Tool {

    private static final double[] FACTORS = {0.299, 0.587, 0.114};

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
                int gray = (int) (r * FACTORS[0] + g * FACTORS[1] + b * FACTORS[2]);
                edited.setRGB(x, y, (gray << 16) + (gray << 8) + gray);
            }
        }
    }

}
