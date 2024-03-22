package ru.nsu.icg.filtershop.model.tools.channels;

import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class GreenChannelTool implements Tool {

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        for (int y = 0; y < original.getHeight(); ++y) {
            for (int x = 0; x < original.getWidth(); ++x) {
                int color = original.getRGB(x, y);
                int g = ColorUtils.getGreen(color);
                result.setRGB(x, y, ColorUtils.getRGB(0, g, 0));
            }
        }
    }
}

