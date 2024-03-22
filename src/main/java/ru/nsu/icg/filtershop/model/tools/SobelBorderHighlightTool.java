package ru.nsu.icg.filtershop.model.tools;


import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;

import static ru.nsu.icg.filtershop.model.tools.RobertsBorderHighlightTool.*;

@AllArgsConstructor
public class SobelBorderHighlightTool implements Tool {

  private final int binarization;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    new BlackWhiteTool().applyTo(original, result);
    int height = result.getHeight();
    int width = result.getWidth();
    int[] pixels = original.getRGB(0, 0, width, height, null, 0, width);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int a = colorAt(pixels, width, height, x - 1, y - 1);
        int b = colorAt(pixels, width, height, x, y - 1);
        int c = colorAt(pixels, width, height, x + 1, y - 1);

        int d = colorAt(pixels, width, height, x - 1, y);
        int f = colorAt(pixels, width, height, x + 1, y);

        int g = colorAt(pixels, width, height, x - 1, y + 1);
        int h = colorAt(pixels, width, height, x, y + 1);
        int i = colorAt(pixels, width, height, x + 1, y + 1);

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

}
