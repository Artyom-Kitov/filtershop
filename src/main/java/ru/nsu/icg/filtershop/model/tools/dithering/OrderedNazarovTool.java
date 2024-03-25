package ru.nsu.icg.filtershop.model.tools.dithering;

import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class OrderedNazarovTool implements Tool {
  private final int quantizationR;
  private final int quantizationG;
  private final int quantizationB;

  private final float[][] matrix;
  private final int[][] quantizations;

  public OrderedNazarovTool(int quantizationR, int quantizationG, int quantizationB) {
    this.quantizationR = quantizationR;
    this.quantizationG = quantizationG;
    this.quantizationB = quantizationB;
    this.matrix = precalculateMatrix(getMatrixSize());
    this.quantizations = new int[][] {
            FloydSteinbergNazarovTool.quantize(quantizationR),
            FloydSteinbergNazarovTool.quantize(quantizationG),
            FloydSteinbergNazarovTool.quantize(quantizationB)
    };
    normalize();
  }

  private float[][] precalculateMatrix(int powerOfTwo) {
    if (powerOfTwo <= 1) {
      return new float[][] {{0, 2},
                            {3, 1}};
    }
    float[][] prev = precalculateMatrix(powerOfTwo - 1);
    int size = (int) Math.pow(2, powerOfTwo);
    float[][] result = new float[size][size];

    for (int y = 0; y < prev.length; ++y) {
      for (int x = 0; x < prev.length; ++x) {
        result[x][y] = prev[x][y] * 4;
        result[x][prev.length + y] = prev[x][y] * 4 + 2;
        result[prev.length + x][y] = prev[x][y] * 4 + 3;
        result[prev.length + x][prev.length + y] = prev[x][y] * 4 + 1;
      }
    }

    return result;
  }

  private int getMatrixSize() {
    float maxStep = 256f / Math.max(Math.max(quantizationR, quantizationG), quantizationB);
    return (int) Math.ceil(Math.log(maxStep) / Math.log(2));
  }

  private void normalize() {
    for (int y = 0; y < matrix.length; ++y) {
      for (int x = 0; x < matrix.length; ++x) {
        matrix[x][y] = (float) (matrix[x][y] / (Math.pow(matrix.length, 2)) - 0.5f);
      }
    }
  }

  @Override
  public void applyTo(BufferedImage original, BufferedImage result) {
    ImageUtils.writeTo(original, result);

    int width = result.getWidth();
    int height = result.getHeight();
    int[] imageRGBs = result.getRGB(0, 0, width, height, null, 0, width);

    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {

        int pos = y * width + x;

        int r = (int) (ColorUtils.getRed(imageRGBs[pos]) +
                          (256f / (quantizations[0].length - 1)) * matrix[y % matrix.length][x % matrix.length]);
        int g = (int) (ColorUtils.getGreen(imageRGBs[pos]) +
                          (256f / (quantizations[1].length - 1)) * matrix[y % matrix.length][x % matrix.length]);
        int b = (int) (ColorUtils.getBlue(imageRGBs[pos]) +
                          (256f / (quantizations[2].length - 1)) * matrix[y % matrix.length][x % matrix.length]);

        int rNew = FloydSteinbergNazarovTool.findClosest(r, quantizations[0]);
        int gNew = FloydSteinbergNazarovTool.findClosest(g, quantizations[1]);
        int bNew = FloydSteinbergNazarovTool.findClosest(b, quantizations[2]);

        imageRGBs[pos] = ColorUtils.getRGB(rNew, gNew, bNew);
      }
    }

    result.setRGB(0, 0, width, height, imageRGBs, 0, width);
  }
}
