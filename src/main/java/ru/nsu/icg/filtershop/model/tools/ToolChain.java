package ru.nsu.icg.filtershop.model.tools;

import lombok.NoArgsConstructor;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ToolChain implements Tool {

    private final List<Tool> tools = new ArrayList<>();

    public ToolChain append(Tool tool) {
        tools.add(tool);
        return this;
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage edited) {
        BufferedImage img1 = ImageUtils.cloneImage(original);
        BufferedImage img2 = edited;
        boolean isTmpLast = true;
        for (Tool tool : tools) {
            tool.applyTo(img1, img2);
            var tmp = img1;
            img1 = img2;
            img2 = tmp;
            isTmpLast = !isTmpLast;
        }
        if (isTmpLast) {
            ImageUtils.writeTo(img1, edited);
        }
    }

}
