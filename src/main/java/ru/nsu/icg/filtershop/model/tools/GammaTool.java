package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class GammaTool implements Tool {

  private float gamma;

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        int r = ColorUtils.getRed(original, x, y);
        int g = ColorUtils.getGreen(original, x, y);
        int b = ColorUtils.getBlue(original, x, y);
        int rNew = (int) (Math.pow((r / 255f), gamma) * 255);
        int gNew = (int) (Math.pow((g / 255f), gamma) * 255);
        int bNew = (int) (Math.pow((b / 255f), gamma) * 255);
        result.setRGB(x, y, ColorUtils.getRGB(rNew, gNew, bNew));
      }
    }
  }

}
