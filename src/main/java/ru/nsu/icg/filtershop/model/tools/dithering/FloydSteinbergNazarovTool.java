package ru.nsu.icg.filtershop.model.tools.dithering;

import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class FloydSteinbergNazarovTool implements Tool {

  private final int[][] quantizations;

  public FloydSteinbergNazarovTool(int quantizationR, int quantizationG, int quantizationB) {
    this.quantizations = new int[][] {
            quantize(quantizationR),
            quantize(quantizationG),
            quantize(quantizationB)
    };
  }

  static int[] quantize(int quantNum) {
    int[] quants = new int[quantNum];
    int step = 255 / (quantNum - 1);
    for (int i = 0; i < quants.length; ++i) {
      quants[i] = i * step;
    }
    return quants;
  }

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    ImageUtils.writeTo(original, result);

    int width = original.getWidth();
    int height = original.getHeight();
    int[] imageRGBs = result.getRGB(0, 0, width, height, null, 0, width);

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int pos = y * width + x;

        int r = ColorUtils.getRed(imageRGBs[pos]);
        int g = ColorUtils.getGreen(imageRGBs[pos]);
        int b = ColorUtils.getBlue(imageRGBs[pos]);

        int closestR = findClosest(r, quantizations[0]);
        int closestG = findClosest(g, quantizations[1]);
        int closestB = findClosest(b, quantizations[2]);

        imageRGBs[pos] = ColorUtils.getRGB(closestR, closestG, closestB);

        int errR = r - closestR;
        int errG = g - closestG;
        int errB = b - closestB;

        propagateError(imageRGBs, width, height, x + 1, y, errR, errG, errB, 7f/16);
        propagateError(imageRGBs, width, height, x - 1, y + 1, errR, errG, errB, 3f/16);
        propagateError(imageRGBs, width, height, x, y + 1, errR, errG, errB, 5f/16);
        propagateError(imageRGBs, width, height, x + 1, y + 1, errR, errG, errB, 1f/16);
      }
    }

    result.setRGB(0, 0, width, height, imageRGBs, 0, width);
  }

  static int findClosest(int color, int[] quantization) {
    int closest = 0;
    int minDistance = Integer.MAX_VALUE;
    for (int q : quantization) {
      int distance = Math.abs(color - q);
      if (distance < minDistance) {
        minDistance = distance;
        closest = q;
        if (minDistance == 0) break;
      }
    }
    return closest;
  }

  private static void propagateError(int[] imageRGBs, int width, int height, int x, int y,
                                     int errR, int errG, int errB, float coef) {

    if (x < 0 || y < 0 || x >= width || y >= height) return;

    int pos = y * width + x;

    int r = ColorUtils.getRed(imageRGBs[pos]);
    int g = ColorUtils.getGreen(imageRGBs[pos]);
    int b = ColorUtils.getBlue(imageRGBs[pos]);

    int newR = Math.min(255, Math.max(0, r + (int) (coef * errR)));
    int newG = Math.min(255, Math.max(0, g + (int) (coef * errG)));
    int newB = Math.min(255, Math.max(0, b + (int) (coef * errB)));

    imageRGBs[pos] = ColorUtils.getRGB(newR, newG, newB);
  }
}
