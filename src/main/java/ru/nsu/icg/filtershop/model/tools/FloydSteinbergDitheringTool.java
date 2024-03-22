package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.utils.ColorUtils;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

/**
 * DON'T EVEN F***ING TRY TO STEAL MY CODE!!!
 * <p/>
 * Author: Artyom Kitov
 * <br/>
 * Date: 16.03.2024
 */
public class FloydSteinbergDitheringTool implements Tool {

    private static final float[] FACTORS = {7f/16, 1f/16, 5f/16, 3f/16};

    private final int quantNumbers;

    public FloydSteinbergDitheringTool(int quantizationR, int quantizationG, int quantizationB) {
        quantNumbers = (quantizationR << 16) | (quantizationG << 8) | quantizationB;
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        ImageUtils.writeTo(original, result);

        for (int mask = 0; mask <= 16; mask += 8) {
            int[] quantization = makeQuantization((quantNumbers & (0xff << mask)) >> mask);
            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                    int color = (result.getRGB(x, y) & (0xff << mask)) >> mask;
                    int closest = findClosest(color, quantization);
                    result.setRGB(x, y, (result.getRGB(x, y) & ~(0xff << mask)) | (closest << mask));
                    int error = color - closest;
                    propagateError(result, x + 1, y, error, mask, FACTORS[0]);
                    propagateError(result, x + 1, y + 1, error, mask, FACTORS[1]);
                    propagateError(result, x, y + 1, error, mask, FACTORS[2]);
                    propagateError(result, x - 1, y + 1, error, mask, FACTORS[3]);
                }
            }
        }
    }

    static int[] makeQuantization(int quantNumber) {
        int[] quantization = new int[quantNumber];
        float step = 255f / (quantization.length - 1);
        for (int i = 0; i < quantization.length; i++) {
            quantization[i] = Math.round(i * step);
        }
        return quantization;
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

    private void propagateError(BufferedImage image, int x, int y, int error, int mask, float factor) {
        if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight()) {
            return;
        }
        int color = (image.getRGB(x, y) & (0xff << mask)) >> mask;
        color = Math.min(Math.max(0, color + (int) (factor * error)), 255);
        image.setRGB(x, y, (image.getRGB(x, y) & ~(0xff << mask)) | (color << mask));
    }

}
