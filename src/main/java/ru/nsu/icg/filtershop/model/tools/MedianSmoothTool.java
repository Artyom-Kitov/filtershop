package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianSmoothTool implements Tool {

  private static final int kernelSize = 5;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    int width = original.getWidth();
    int height = original.getHeight();

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        List<Integer> redValues = new ArrayList<>();
        List<Integer> greenValues = new ArrayList<>();
        List<Integer> blueValues = new ArrayList<>();

        for (int dy = -kernelSize / 2; dy <= kernelSize / 2; dy++) {
          for (int dx = -kernelSize / 2; dx <= kernelSize / 2; dx++) {
            int nx = Math.min(Math.max(x + dx, 0), width - 1);
            int ny = Math.min(Math.max(y + dy, 0), height - 1);

            int color = original.getRGB(nx, ny);
            redValues.add(ColorUtils.getRed(color));
            greenValues.add(ColorUtils.getGreen(color));
            blueValues.add(ColorUtils.getBlue(color));
          }
        }

        Collections.sort(redValues);
        Collections.sort(greenValues);
        Collections.sort(blueValues);

        int medianRed = redValues.get(redValues.size() / 2);
        int medianGreen = greenValues.get(greenValues.size() / 2);
        int medianBlue = blueValues.get(blueValues.size() / 2);

        result.setRGB(x, y, ColorUtils.getRGB(medianRed, medianGreen, medianBlue));
      }
    }
  }
}
