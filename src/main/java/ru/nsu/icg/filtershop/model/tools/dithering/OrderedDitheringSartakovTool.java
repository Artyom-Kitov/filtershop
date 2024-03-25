package ru.nsu.icg.filtershop.model.tools.dithering;

import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class OrderedDitheringSartakovTool implements Tool {
    private final int redQuantizationNumber;
    private final int greenQuantizationNumber;
    private final int blueQuantizationNumber;

    private final float[][] matrix;

    public OrderedDitheringSartakovTool(int quantR, int quantG, int quantB) {
        redQuantizationNumber = quantR;
        greenQuantizationNumber = quantG;
        blueQuantizationNumber = quantB;

        matrix = createOrderedDitheringMatrix(getMatrixSize());

        normalizeMatrix();
    }

    private int getMatrixSize() {
        float maxStep = 256f / Math.max(Math.max(redQuantizationNumber, greenQuantizationNumber), blueQuantizationNumber);

        int powerOfTwo = 1;
        while (maxStep > (1 << powerOfTwo) * (1 << powerOfTwo)) {
            ++powerOfTwo;
        }

        return  powerOfTwo;
    }

    private float[][] createOrderedDitheringMatrix(int powerOfTwo) {
        if (powerOfTwo <= 1) {
            return new float[][] {
                    { 0f, 2f },
                    { 3f, 1f }
            };
        }

        float[][] prev = createOrderedDitheringMatrix(powerOfTwo - 1);
        float[][] result = new float[1 << powerOfTwo][1 << powerOfTwo];
        for (int y = 0; y < prev.length; ++y) {
            for (int x = 0; x < prev.length; ++x) {
                // 0 2
                // 3 1
                result[x][y] = 4 * prev[x][y];
                result[x][prev.length + y] = 4 * prev[x][y] + 2;
                result[prev.length + x][y] = 4 * prev[x][y] + 3;
                result[prev.length + x][prev.length + y] = 4 * prev[x][y] + 1;
            }
        }

        return result;
    }

    private void normalizeMatrix() {
        for (int y = 0; y < matrix.length; ++y) {
            for (int x = 0; x < matrix.length; ++x) {
                matrix[x][y] = matrix[x][y] / (matrix.length * matrix.length) - 0.5f;
            }
        }
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        ImageUtils.writeTo(original, result);

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = result.getRGB(0, 0, width, height, null, 0, width);
        int[][] quantization = {
                FloydSteinbergSartakovTool.quantizeColor(redQuantizationNumber),
                FloydSteinbergSartakovTool.quantizeColor(greenQuantizationNumber),
                FloydSteinbergSartakovTool.quantizeColor(blueQuantizationNumber)
        };

        for (int y = 0; y < result.getHeight(); ++y) {
            for (int x = 0; x < result.getWidth(); ++x) {
                float step = 256f;
                int curPixelPos = y * width + x;

                int r = ColorUtils.getRed(pixels[curPixelPos]);
                int g = ColorUtils.getGreen(pixels[curPixelPos]);
                int b = ColorUtils.getBlue(pixels[curPixelPos]);

                int rNormalized = (int) (r + step * matrix[y % matrix.length][x % matrix.length]);
                int gNormalized = (int) (g + step * matrix[y % matrix.length][x % matrix.length]);
                int bNormalized = (int) (b + step * matrix[y % matrix.length][x % matrix.length]);

                int newR = FloydSteinbergSartakovTool.findClosest(rNormalized, quantization[0]);
                int newG = FloydSteinbergSartakovTool.findClosest(gNormalized, quantization[1]);
                int newB = FloydSteinbergSartakovTool.findClosest(bNormalized, quantization[2]);

                pixels[curPixelPos] = ColorUtils.getRGB(newR, newG, newB);
            }
        }
        result.setRGB(0, 0, width, height, pixels, 0, width);
    }
}
