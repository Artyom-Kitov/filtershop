package ru.nsu.icg.filtershop.model.tools;

import lombok.RequiredArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

@RequiredArgsConstructor
public class GammaTool implements Tool {

  private final float gamma;

  @Override
  public void applyTo(BufferedImage original, BufferedImage edited) {
    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        int color = original.getRGB(x, y);
        int r = ColorUtils.getRed(color);
        int g = ColorUtils.getGreen(color);
        int b = ColorUtils.getBlue(color);
        int rNew = (int) (Math.pow((r / 255f), gamma) * 255);
        int gNew = (int) (Math.pow((g / 255f), gamma) * 255);
        int bNew = (int) (Math.pow((b / 255f), gamma) * 255);
        edited.setRGB(x, y, (rNew << 16) + (gNew << 8) + bNew);
      }
    }
  }

}
