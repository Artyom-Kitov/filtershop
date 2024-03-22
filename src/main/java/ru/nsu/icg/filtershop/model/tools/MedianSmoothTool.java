package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MedianSmoothTool implements Tool {

  private final int n;

  public MedianSmoothTool(int n) {
    this.n = n;
  }

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    int width = original.getWidth();
    int height = original.getHeight();

    int[] redValues = new int[n * n];
    int[] greenValues = new int[n * n];
    int[] blueValues = new int[n * n];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int index = 0;
        for (int dy = -n / 2; dy <= n / 2; dy++) {
          for (int dx = -n / 2; dx <= n / 2; dx++) {
            int nx = Math.min(Math.max(x + dx, 0), width - 1);
            int ny = Math.min(Math.max(y + dy, 0), height - 1);

            int color = original.getRGB(nx, ny);
            redValues[index] = ColorUtils.getRed(color);
            greenValues[index] = ColorUtils.getGreen(color);
            blueValues[index] = ColorUtils.getBlue(color);
            index++;
          }
        }

        Arrays.sort(redValues);
        Arrays.sort(greenValues);
        Arrays.sort(blueValues);

        int medianRed = redValues[redValues.length / 2];
        int medianGreen = greenValues[greenValues.length / 2];
        int medianBlue = blueValues[blueValues.length / 2];

        result.setRGB(x, y, ColorUtils.getRGB(medianRed, medianGreen, medianBlue));
      }
    }
  }
}
