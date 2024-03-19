package ru.nsu.icg.filtershop.model;

import lombok.Getter;
import lombok.Setter;
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

    @Setter
    private BufferedImage modified;

    private BufferedImage filtered;

    private BufferedImage rotated;

    private int rotatingAngle = 0;

    public void setRotatingAngle(int rotatingAngle) {
        this.rotatingAngle = rotatingAngle;
        rotated = ImageUtils.getRotatedImage(filtered, rotatingAngle);
    }

    public RGBMatrix(int width, int height) {
        setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    }

    public void setImage(BufferedImage image) {
        original = image;
        modified = ImageUtils.cloneImage(image);
        filtered = ImageUtils.cloneImage(image);
        rotated = ImageUtils.getRotatedImage(filtered, rotatingAngle);
    }

    public void applyTool(Tool tool) {
        tool.applyTo(modified, filtered);
    }

    public void reset() {
        setImage(original);
    }

}
