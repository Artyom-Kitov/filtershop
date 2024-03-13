package ru.nsu.icg.filtershop.model.tools;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

@AllArgsConstructor
public class GammaTool implements Tool {

  @Getter
  @Setter
  private float gammaR, gammaG, gammaB;


  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    for (int y = 0; y < original.getHeight(); y++) {
      for (int x = 0; x < original.getWidth(); x++) {
        int color = original.getRGB(x, y);
        int r = ColorUtils.getRed(color);
        int g = ColorUtils.getGreen(color);
        int b = ColorUtils.getBlue(color);
        int rNew = (int) (Math.pow((r / 255f), gammaR) * 255);
        int gNew = (int) (Math.pow((g / 255f), gammaG) * 255);
        int bNew = (int) (Math.pow((b / 255f), gammaB) * 255);
        result.setRGB(x, y, ColorUtils.getRGB(rNew, gNew, bNew));
      }
    }
  }

}
