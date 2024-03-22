package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class PixelArtTool implements Tool {

  private int blockSize;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    int width = original.getWidth();
    int height = original.getHeight();
    int[] pixels = original.getRGB(0, 0, width, height, null, 0, width);

    for (int y = 0; y < height; y += blockSize) {
      for (int x = 0; x < width; x += blockSize) {
        int avgR = 0, avgG = 0, avgB = 0;
        int totalPixels = 0;

        for (int yy = y; yy < y + blockSize && yy < height; yy++) {
          for (int xx = x; xx < x + blockSize && xx < width; xx++) {
            int color = pixels[yy * width + xx];
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

        int avgColor = ColorUtils.getRGB(avgR, avgG, avgB);

        for (int yy = y; yy < y + blockSize && yy < height; yy++) {
          for (int xx = x; xx < x + blockSize && xx < width; xx++) {
            result.setRGB(xx, yy, avgColor);
          }
        }
      }
    }
  }

}
