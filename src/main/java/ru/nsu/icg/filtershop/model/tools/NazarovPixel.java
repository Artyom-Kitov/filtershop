package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.awt.image.BufferedImage;

@AllArgsConstructor
public class NazarovPixel implements Tool {

  // TODO: from 0 to 50
  private int blockSize;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    int width = original.getWidth();
    int height = original.getHeight();

    for (int y = 0; y < height; y += blockSize) {
      for (int x = 0; x < width; x += blockSize) {
        int avgR = 0, avgG = 0, avgB = 0;
        int totalPixels = 0;

        for (int yy = y; yy < y + blockSize && yy < height; yy++) {
          for (int xx = x; xx < x + blockSize && xx < width; xx++) {
            int color = original.getRGB(xx, yy);
            avgR += (color >> 16) & 0xFF;
            avgG += (color >> 8) & 0xFF;
            avgB += color & 0xFF;
            totalPixels++;
          }
        }

        if (totalPixels != 0) {
          avgR /= totalPixels;
          avgG /= totalPixels;
          avgB /= totalPixels;
        }

        Color avgColor = new Color(avgR, avgG, avgB);

        for (int yy = y; yy < y + blockSize && yy < height; yy++) {
          for (int xx = x; xx < x + blockSize && xx < width; xx++) {
            result.setRGB(xx, yy, avgColor.getRGB());
          }
        }
      }
    }
  }

}
