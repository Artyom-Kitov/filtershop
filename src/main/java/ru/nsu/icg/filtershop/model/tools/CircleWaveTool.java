package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class CircleWaveTool implements Tool {

  private int intensity;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    int centerX = original.getWidth() / 2;
    int centerY = original.getHeight() / 2;

    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        int dx = x - centerX;
        int dy = y - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double angle = Math.atan2(dy, dx);

        double shiftFactor = intensity * Math.sin(distance / 20.0);

        int newX = (int) (x + shiftFactor * Math.cos(angle));
        int newY = (int) (y + shiftFactor * Math.sin(angle));

        if (newX >= 0 && newX < original.getWidth() && newY >= 0 && newY < original.getHeight()) {
          int color = original.getRGB(newX, newY);
          result.setRGB(x, y, color);
        }
      }
    }
  }
}
