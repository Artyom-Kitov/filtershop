package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class GammaTool implements Tool {

  private float gamma;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    int width = original.getWidth();
    int height = original.getHeight();
    int[] pixels = original.getRGB(0, 0, width, height, null, 0, width);
    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        int color = pixels[y * width + x];
        int r = ColorUtils.getRed(color);
        int g = ColorUtils.getGreen(color);
        int b = ColorUtils.getBlue(color);
        int rNew = (int) (Math.pow((r / 255f), gamma) * 255);
        int gNew = (int) (Math.pow((g / 255f), gamma) * 255);
        int bNew = (int) (Math.pow((b / 255f), gamma) * 255);
        result.setRGB(x, y, ColorUtils.getRGB(rNew, gNew, bNew));
      }
    }
  }

}
