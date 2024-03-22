package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;

import java.awt.image.BufferedImage;

public class EmbossingTool implements Tool {

  FilterTool embossing;

  public EmbossingTool() {
    embossing = new FilterTool(new float[][] {
            {0, -1, 0},
            {-1,  0, 1},
            { 0,  1, 0}
    });
  }

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    embossing.applyTo(original, result);

    for (int y = 0; y < result.getHeight(); y++) {
      for (int x = 0; x < result.getWidth(); x++) {
        int c = result.getRGB(x, y);
        int r = ColorUtils.getRed(c);
        int g = ColorUtils.getGreen(c);
        int b = ColorUtils.getBlue(c);
        int rNew = Math.min((r + 128), 255);
        int gNew = Math.min((g + 128), 255);
        int bNew = Math.min((b + 128), 255);
        result.setRGB(x, y, ColorUtils.getRGB(rNew, gNew, bNew));
      }
    }
  }

}
