package ru.nsu.icg.filtershop.model.tools;


import java.awt.image.BufferedImage;

public class SharpnessTool implements Tool {

  private final FilterTool filter;

  public SharpnessTool() {
    filter = new FilterTool(new float[][] {
            {0,  -1,  0},
            {-1,  5, -1},
            { 0, -1,  0}
    });
  }

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {

    filter.applyTo(original, result);

  }


}
