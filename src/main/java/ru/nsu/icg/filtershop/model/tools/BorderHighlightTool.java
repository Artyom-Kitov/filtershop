package ru.nsu.icg.filtershop.model.tools;


import java.awt.image.BufferedImage;

public class BorderHighlightTool implements Tool {

  FilterTool borderHighlight;

  public BorderHighlightTool() {
    borderHighlight = new FilterTool(new float[][] {
            {0,  -1,  0},
            {-1,  4, -1},
            { 0, -1,  0}
    });
  }

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {

    borderHighlight.applyTo(original, result);

  }


}
