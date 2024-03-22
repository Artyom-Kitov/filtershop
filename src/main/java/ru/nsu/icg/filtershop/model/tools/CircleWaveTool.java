package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class CircleWaveTool implements Tool {

  private int intensity;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    ImageUtils.writeTo(original, result);
    int centerX = original.getWidth() / 2;
    int centerY = original.getHeight() / 2;

    int width = original.getWidth();
    int height = original.getHeight();
    int[] pixels = original.getRGB(0, 0, width, height, null, 0, width);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int dx = x - centerX;
        int dy = y - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double angle = Math.atan2(dy, dx);

        double shiftFactor = intensity * Math.sin(distance / 20.0);

        int newX = (int) (x + shiftFactor * Math.cos(angle));
        int newY = (int) (y + shiftFactor * Math.sin(angle));

        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
          int color = pixels[newY * width + newX];
          result.setRGB(x, y, color);
        }
      }
    }
  }
}
