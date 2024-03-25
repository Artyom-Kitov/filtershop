package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class SwirlTool implements Tool {

  private int swirlFactor;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    ImageUtils.writeTo(original, result);

    int width = original.getWidth();
    int height = original.getHeight();
    int centerX = width / 2;
    int centerY = height / 2;

    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {

        double dx = x - centerX;
        double dy = y - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        double angle = Math.atan2(dy, dx);

        double swirl = swirlFactor * distance / Math.sqrt(width * width + height * height);

        int newX = (int) (centerX + distance * Math.cos(angle + swirl));
        int newY = (int) (centerY + distance * Math.sin(angle + swirl));

        newX = Math.min(Math.max(newX, 0), width - 1);
        newY = Math.min(Math.max(newY, 0), height - 1);

        int rgb = result.getRGB(newX, newY);
        result.setRGB(x, y, rgb);
      }
    }

  }
}
