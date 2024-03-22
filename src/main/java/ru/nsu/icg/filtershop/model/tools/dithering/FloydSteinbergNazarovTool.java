package ru.nsu.icg.filtershop.model.tools.dithering;

import ru.nsu.icg.filtershop.model.tools.Tool;

import java.awt.image.BufferedImage;

public class FloydSteinbergNazarovTool implements Tool {

  private final int quantizationR, quantizationG, quantizationB;

  public FloydSteinbergNazarovTool(int quantizationR, int quantizationG, int quantizationB) {
    this.quantizationR = quantizationR;
    this.quantizationG = quantizationG;
    this.quantizationB = quantizationB;
  }

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {

  }
}
