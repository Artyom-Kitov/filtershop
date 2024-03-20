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
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int s = Math.abs(colorAt(original, x, y) - colorAt(original, x + 1, y + 1));
                s += Math.abs(colorAt(original, x, y + 1) - colorAt(original, x + 1, y));
                if (s > binarization) {
                    result.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    result.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
    }

    private int colorAt(BufferedImage image, int x, int y) {
        int xFixed = Math.max(0, Math.min(x, image.getWidth() - 1));
        int yFixed = Math.max(0, Math.min(y, image.getHeight() - 1));
        return ColorUtils.getMiddleRGB(ColorUtils.getRGB(image, xFixed, yFixed));
    }

}
