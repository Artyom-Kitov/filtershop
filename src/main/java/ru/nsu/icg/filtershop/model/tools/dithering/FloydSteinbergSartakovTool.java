package ru.nsu.icg.filtershop.model.tools.dithering;

import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ColorUtils;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class FloydSteinbergSartakovTool implements Tool {

    private static final float[] ERROR_COEFFICIENTS = { 7f/16, 3f/16, 5f/16, 1f/16 };
    private final int redQuantizationNumber;
    private final int greenQuantizationNumber;
    private final int blueQuantizationNumber;

    public FloydSteinbergSartakovTool(int quantR, int quantG, int quantB) {
        redQuantizationNumber = quantR;
        greenQuantizationNumber = quantG;
        blueQuantizationNumber = quantB;
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        ImageUtils.writeTo(original, result);

        int width = original.getWidth();
        int height = original.getHeight();
        int[] pixels = result.getRGB(0, 0, width, height, null, 0, width);
        int[][] quantization = {
                quantizeColor(redQuantizationNumber),
                quantizeColor(greenQuantizationNumber),
                quantizeColor(blueQuantizationNumber)
        };
        for (int y = 0; y < result.getHeight(); ++y) {
            for (int x = 0; x < result.getWidth(); ++x) {
                int curPixelPos = y * width + x;

                int r = ColorUtils.getRed(pixels[curPixelPos]);
                int g = ColorUtils.getGreen(pixels[curPixelPos]);
                int b = ColorUtils.getBlue(pixels[curPixelPos]);

                int closestR = findClosest(r, quantization[0]);
                int closestG = findClosest(g, quantization[1]);
                int closestB = findClosest(b, quantization[2]);

                pixels[curPixelPos] = ColorUtils.getRGB(closestR, closestG, closestB);

                int[] errors = {
                        r - closestR,
                        g - closestG,
                        b - closestB
                };

                propagateError(pixels, width, height, x + 1, y, errors, ERROR_COEFFICIENTS[0]);
                propagateError(pixels, width, height, x - 1, y + 1, errors, ERROR_COEFFICIENTS[1]);
                propagateError(pixels, width, height, x, y + 1, errors, ERROR_COEFFICIENTS[2]);
                propagateError(pixels, width, height, x + 1, y + 1, errors, ERROR_COEFFICIENTS[3]);
            }
        }

        result.setRGB(0, 0, width, height, pixels, 0, width);
    }

    static int[] quantizeColor(int quantizationNumber) {
        int[] quantizedColorValues = new int[quantizationNumber];
        float step = 255f / (quantizedColorValues.length - 1);
        for (int i = 0; i < quantizedColorValues.length; ++i) {
            quantizedColorValues[i] = Math.round(i * step);
        }

        return quantizedColorValues;
    }

    static int findClosest(int color, int[] quantization) {
        int closest = 0;
        int minDistance = Integer.MAX_VALUE;
        for (int q : quantization) {
            int distance = Math.abs(color - q);
            if (distance < minDistance) {
                minDistance = distance;
                closest = q;
            }
        }

        return closest;
    }

    private void propagateError(int[] pixels, int width, int height, int x, int y, int[] errors, float coefficient) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }
        int curPos = y * width + x;

        int r = ColorUtils.getRed(pixels[curPos]);
        int rErrorCoefficient = (int) (coefficient * errors[0]);

        int g = ColorUtils.getGreen(pixels[curPos]);
        int gErrorCoefficient = (int) (coefficient * errors[1]);

        int b = ColorUtils.getBlue(pixels[curPos]);
        int bErrorCoefficient = (int) (coefficient * errors[2]);

        int newR = Math.min(255, Math.max(0, r + rErrorCoefficient));
        int newG = Math.min(255, Math.max(0, g + gErrorCoefficient));
        int newB = Math.min(255, Math.max(0, b + bErrorCoefficient));

        pixels[curPos] = ColorUtils.getRGB(newR, newG, newB);
    }
}
