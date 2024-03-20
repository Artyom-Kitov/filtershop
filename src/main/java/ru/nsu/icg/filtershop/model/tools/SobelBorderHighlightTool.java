package ru.nsu.icg.filtershop.model.tools;


import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

@AllArgsConstructor
public class SobelBorderHighlightTool implements Tool {

  private final int binarization;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    new BlackWhiteTool().applyTo(original, result);
    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        int a = colorAt(original, x - 1, y - 1);
        int b = colorAt(original, x, y - 1);
        int c = colorAt(original, x + 1, y - 1);

        int d = colorAt(original, x - 1, y);
        int f = colorAt(original, x + 1, y);

        int g = colorAt(original, x - 1, y + 1);
        int h = colorAt(original, x, y + 1);
        int i = colorAt(original, x + 1, y + 1);

        int sx = (c + 2 * f + i) - (a + 2 * d + g);
        int sy = (g + 2 * h + i) - (a + 2 * b + c);
        int s = Math.abs(sx) + Math.abs(sy);
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
