package ru.nsu.icg.filtershop.model.tools.dithering;

import ru.nsu.icg.filtershop.model.tools.Tool;
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

        int width = original.getWidth();
        int height = original.getHeight();
        int[] pixels = result.getRGB(0, 0, width, original.getHeight(), null, 0, width);
        for (int mask = 0; mask <= 16; mask += 8) {
            int[] quantization = makeQuantization((quantNumbers & (0xff << mask)) >> mask);
            for (int y = 0; y < result.getHeight(); y++) {
                for (int x = 0; x < result.getWidth(); x++) {
                    int color = (pixels[y * width + x] & (0xff << mask)) >> mask;
                    int closest = findClosest(color, quantization);
                    pixels[y * width + x] = (pixels[y * width + x] & ~(0xff << mask)) | (closest << mask);
                    int error = color - closest;
                    propagateError(pixels, width, height, x + 1, y, error, mask, FACTORS[0]);
                    propagateError(pixels, width, height, x + 1, y + 1, error, mask, FACTORS[1]);
                    propagateError(pixels, width, height, x, y + 1, error, mask, FACTORS[2]);
                    propagateError(pixels, width, height, x - 1, y + 1, error, mask, FACTORS[3]);
                }
            }
        }
        result.setRGB(0, 0, width, height, pixels, 0, width);
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

    private void propagateError(int[] pixels, int width, int height, int x, int y, int error, int mask, float factor) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }
        int color = (pixels[y * width + x] & (0xff << mask)) >> mask;
        color = Math.min(Math.max(0, color + (int) (factor * error)), 255);
        pixels[y * width + x] = (pixels[y * width + x] & ~(0xff << mask)) | (color << mask);
    }

}
