package ru.nsu.icg.filtershop.model.tools;

import java.awt.image.BufferedImage;

public class BlurTool implements Tool {

    private final FilterTool filter;

    public BlurTool(float sigma, int n) {
        if (sigma == 0f) {
            throw new IllegalArgumentException("zero standard deviation");
        }
        float[][] matrix = new float[n][n];
        float sum = 0f;
        for (int y = -n / 2; y <= n / 2; y++) {
            for (int x = -n / 2; x <= n / 2; x++) {
                matrix[n / 2 + y][n / 2 + x] = (float) (Math.exp((-x * x - y * y) / (2. * sigma * sigma))
                        / (2. * Math.PI * sigma * sigma));
                sum += matrix[n / 2 + y][n / 2 + x];
            }
        }
        if (sum == 0f) {
            throw new IllegalStateException("zero gaussian matrix");
        }
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                matrix[y][x] /= sum;
            }
        }
        filter = new FilterTool(matrix);
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        filter.applyTo(original, result);
    }

}
