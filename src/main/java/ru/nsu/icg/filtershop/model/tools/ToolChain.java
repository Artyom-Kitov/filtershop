package ru.nsu.icg.filtershop.model.tools;

import lombok.NoArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


/**
 * A class for building a chain of image conversions, which is an image conversion itself.
 * The tools are being applied in the order they were appended.
 * <br/>
 * Example:
 * <pre>
 *     BufferedImage original;
 *     BufferedImage result;
 *
 *     ToolChain toolChain = new ToolChain();
 *     toolChain.append(new BlackWhiteTool())
 *              .append(new InversionTool())
 *              .applyTo(original, result);
 * </pre>
 * This code applies a {@link BlackWhiteTool} and then an {@link InversionTool} to the original image.
 * <p/>
 * Author: Artyom Kitov
 * <br/>
 * Date: 12.03.2024
 */
@NoArgsConstructor
public class ToolChain implements Tool {

    private final List<Tool> tools = new ArrayList<>();

    public ToolChain append(Tool tool) {
        tools.add(tool);
        return this;
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        BufferedImage img1 = ImageUtils.cloneImage(original);
        BufferedImage img2 = result;
        boolean isTmpLast = true;
        for (Tool tool : tools) {
            tool.applyTo(img1, img2);
            var tmp = img1;
            img1 = img2;
            img2 = tmp;
            isTmpLast = !isTmpLast;
        }
        if (isTmpLast) {
            ImageUtils.writeTo(img1, result);
        }
    }

}
