package ru.nsu.icg.filtershop.model.tools;

import lombok.Setter;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class FilterTool implements Tool {

    public FilterTool(float[][] filter) {
        if (filter == null || filter.length == 0) {
            throw new IllegalArgumentException("empty filter matrix");
        }
        if (filter.length != filter[0].length) {
            throw new IllegalArgumentException("filter matrix must be squared");
        }
        if (filter.length % 2 == 0) {
            throw new IllegalArgumentException("filter matrix size must be an odd positive integer");
        }
        this.filter = filter;
    }

    @Setter
    private float[][] filter;

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        int n = (filter.length - 1) / 2;
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                float r = 0;
                float g = 0;
                float b = 0;
                for (int dx = -n; dx <= n; dx++) {
                    for (int dy = -n; dy <= n; dy++) {
                        int xFixed = Math.min(Math.max(x, n), original.getWidth() - n - 1);
                        int yFixed = Math.min(Math.max(y, n), original.getHeight() - n - 1);

                        int color = original.getRGB(xFixed + dx, yFixed + dy);
                        float factor = filter[n + dy][n + dx];
                        r += ColorUtils.getRed(color) * factor;
                        g += ColorUtils.getGreen(color) * factor;
                        b += ColorUtils.getBlue(color) * factor;
                    }
                }
                int newR = Math.min(Math.max((int) r, 0), 255);
                int newG = Math.min(Math.max((int) g, 0), 255);
                int newB = Math.min(Math.max((int) b, 0), 255);
                result.setRGB(x, y, ColorUtils.getRGB(newR, newG, newB));
            }
        }
    }

}
