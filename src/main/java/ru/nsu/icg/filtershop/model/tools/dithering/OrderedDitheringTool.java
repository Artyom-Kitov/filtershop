package ru.nsu.icg.filtershop.model.tools.dithering;

import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

import static ru.nsu.icg.filtershop.model.tools.dithering.FloydSteinbergDitheringTool.*;

/**
 * DON'T EVEN F***ING TRY TO STEAL MY CODE!!!
 * <p/>
 * Author: Artyom Kitov
 * <br/>
 * Date: 22.03.2024
 */
public class OrderedDitheringTool implements Tool {

    private final int quantNumbers;

    private final float[][] matrix;
    private float maxStep;

    public OrderedDitheringTool(int quantizationR, int quantizationG, int quantizationB) {
        quantNumbers = (quantizationR << 16) | (quantizationG << 8) | quantizationB;
        matrix = buildMatrix(getMatrixSize());
        normalize();
    }

    private int getMatrixSize() {
        int quantizationR = (quantNumbers & 0xff0000) >> 16;
        int quantizationG = (quantNumbers & 0x00ff00) >> 8;
        int quantizationB = quantNumbers & 0x0000ff;
        maxStep = Math.max(Math.max(256f / quantizationR, 256f / quantizationG), 256f / quantizationB);

        // the power of 2
        int result = 1;
        while (maxStep > (1 << result) * (1 << result)) {
            result++;
        }
        return result;
    }

    private float[][] buildMatrix(int powerOfTwo) {
        if (powerOfTwo <= 1) {
            return new float[][]{
                    {0, 2},
                    {3, 1}
            };
        }
        float[][] prev = buildMatrix(powerOfTwo - 1);
        float[][] result = new float[1 << powerOfTwo][1 << powerOfTwo];
        for (int y = 0; y < prev.length; y++) {
            for (int x = 0; x < prev.length; x++) {
                result[y][x] = 4 * prev[y][x];
                result[y][prev.length + x] = 4 * prev[y][x] + 2;
                result[prev.length + y][x] = 4 * prev[y][x] + 3;
                result[prev.length + y][prev.length + x] = 4 * prev[y][x] + 1;
            }
        }
        return result;
    }

    private void normalize() {
        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                matrix[y][x] /= matrix.length * matrix.length;
                matrix[y][x] -= 0.5f;
            }
        }
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        ImageUtils.writeTo(original, result);

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = result.getRGB(0, 0, width, height, null, 0, width);
        for (int mask = 0; mask <= 16; mask += 8) {
            int[] quantization = makeQuantization((quantNumbers & (0xff << mask)) >> mask);
            float step = 256f / (quantization.length - 1);
            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                    int color = (pixels[y * width + x] & (0xff << mask)) >> mask;
                    int normalized = (int) (color + step * matrix[y % matrix.length][x % matrix.length]);
                    int newColor = findClosest(normalized, quantization);
                    pixels[y * width + x] = (pixels[y * width + x] & ~(0xff << mask)) | (newColor << mask);
                }
            }
        }
        result.setRGB(0, 0, width, height, pixels, 0, width);
    }

}
