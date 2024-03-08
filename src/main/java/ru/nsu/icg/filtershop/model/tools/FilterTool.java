package ru.nsu.icg.filtershop.model.tools;

import lombok.RequiredArgsConstructor;
import ru.nsu.icg.filtershop.model.RGBMatrix;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class FilterTool implements Tool {

    private final float[][] mask;

    @Override
    public void applyTo(RGBMatrix matrix) {
        int n = (mask.length - 1) / 2;
        BufferedImage resized = matrix.getResized();
        BufferedImage edited = matrix.getEdited();
        for (int y = n; y < resized.getHeight() - n; y++) {
            for (int x = n; x < resized.getWidth() - n; x++) {
                float red = 0;
                float green = 0;
                float blue = 0;
                for (int dx = -n; dx <= n; dx++) {
                    for (int dy = -n; dy <= n; dy++) {
                        int color = resized.getRGB(x + dx, y + dy);
                        float factor = mask[n + dy][n + dx];
                        red += ((color & 0xff0000) >> 16) * factor;
                        green += ((color & 0x00ff00) >> 8) * factor;
                        blue += (color & 0x0000ff) * factor;
                    }
                }
                int newRed = Math.min(Math.max((int) red, 0), 255);
                int newGreen = Math.min(Math.max((int) green, 0), 255);
                int newBlue = Math.min(Math.max((int) blue, 0), 255);
                edited.setRGB(x, y, (newRed << 16) + (newGreen << 8) + (newBlue));
            }
        }
    }

}
