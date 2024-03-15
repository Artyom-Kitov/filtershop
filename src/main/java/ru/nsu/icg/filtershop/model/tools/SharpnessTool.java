package ru.nsu.icg.filtershop.model.tools;


import java.awt.image.BufferedImage;

public class SharpnessTool implements Tool {

  FilterTool sharpnessTool;

  public SharpnessTool() {
    sharpnessTool = new FilterTool(new float[][] {
            {0,  -1,  0},
            {-1,  5, -1},
            { 0, -1,  0}
    });
  }

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {

    sharpnessTool.applyTo(original, result);

  }


}
