package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

@AllArgsConstructor
public class RobertsBorderHighlightTool implements Tool {

    private final int binarization;

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        int width = original.getWidth();
        int height = original.getHeight();
        int[] pixels = original.getRGB(0, 0, width, height, null, 0, width);
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int s = Math.abs(colorAt(pixels, width, height, x, y) -
                        colorAt(pixels, width, height, x + 1, y + 1));
                s += Math.abs(colorAt(pixels, width, height, x, y + 1) -
                        colorAt(pixels, width, height, x + 1, y));
                if (s > binarization) {
                    result.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    result.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
    }

    static int colorAt(int[] pixels, int width, int height, int x, int y) {
        int xFixed = Math.max(0, Math.min(x, width - 1));
        int yFixed = Math.max(0, Math.min(y, height - 1));
        return ColorUtils.getMiddleRGB(pixels[yFixed * width + xFixed]);
    }

}
