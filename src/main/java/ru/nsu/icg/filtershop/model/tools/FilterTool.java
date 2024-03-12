package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class FilterTool implements Tool {

    @Setter
    private float[][] mask;

    @Override
    public void applyTo(BufferedImage original, BufferedImage edited) {
        int n = (mask.length - 1) / 2;
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                float red = 0;
                float green = 0;
                float blue = 0;
                for (int dx = -n; dx <= n; dx++) {
                    for (int dy = -n; dy <= n; dy++) {
                        int xFixed = Math.min(Math.max(x, n), original.getWidth() - n - 1);
                        int yFixed = Math.min(Math.max(y, n), original.getHeight() - n - 1);

                        int color = original.getRGB(xFixed + dx, yFixed + dy);
                        float factor = mask[n + dy][n + dx];
                        red += ColorUtils.getRed(color) * factor;
                        green += ColorUtils.getGreen(color) * factor;
                        blue += ColorUtils.getBlue(color) * factor;
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
