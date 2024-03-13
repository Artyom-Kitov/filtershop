package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.RGBMatrix;

import java.awt.image.BufferedImage;

/**
 * An interface for image processing.
 * To create your tool implementation,
 * implement {@link #applyTo(BufferedImage original, BufferedImage edited)} method.
 * <br/>
 * <p/>
 * See also: {@link #applyTo(BufferedImage, BufferedImage)}
 * <p/>
 * Author: Artyom Kitov
 * <br/>
 * Date: 07.03.2024
 */
@FunctionalInterface
public interface Tool {

    /**
     * This method should be implemented for image processing.
     * @param original The image to be processed. <b>Warning:</b> do not make any changes there!
     * @param result The resulting image.
     */
    void applyTo(BufferedImage original, BufferedImage result);

}
