package ru.nsu.icg.filtershop.model;

import lombok.Getter;
import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class stores the original image, its modified copy and the filtered image.
 * By default, the modified image and the filtered one are copies of the original image.
 * <p/>
 * Author: Artyom Kitov
 * <br/>
 * Date: 06.03.2024
 */
@Getter
public class RGBMatrix {

    private BufferedImage original;
    private BufferedImage filtered;
    private BufferedImage rotated;
    private BufferedImage rotatedFiltered;
    private BufferedImage result;

    private int rotatingAngle = 0;

    public void setRotatingAngle(int rotatingAngle) {
        boolean swapped = isSwapped();
        this.rotatingAngle = rotatingAngle;
        rotated = ImageUtils.getRotatedImage(original, rotatingAngle);
        rotatedFiltered = ImageUtils.getRotatedImage(filtered, rotatingAngle);
        if (swapped) {
            result = rotated;
        } else {
            result = rotatedFiltered;
        }
    }

    public RGBMatrix(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, 0xf0f0f0);
            }
        }
        setImage(image);
    }

    public void setImage(BufferedImage image) {
        if (image == original) {
            swap();
            return;
        }
        original = image;
        rotated = ImageUtils.getRotatedImage(original, rotatingAngle);
        filtered = ImageUtils.cloneImage(image);
        rotatedFiltered = ImageUtils.getRotatedImage(filtered, rotatingAngle);
        result = rotatedFiltered;
    }

    public void applyTool(Tool tool) {
        tool.applyTo(original, filtered);
        rotatedFiltered = ImageUtils.getRotatedImage(filtered, rotatingAngle);
        result = rotatedFiltered;
    }

    public void swap() {
        if (result == rotatedFiltered) {
            result = rotated;
        } else {
            result = rotatedFiltered;
        }
    }

    public boolean isSwapped() {
        return result == rotated;
    }

}
