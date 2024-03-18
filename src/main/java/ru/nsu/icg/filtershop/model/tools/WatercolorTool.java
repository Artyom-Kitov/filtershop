package ru.nsu.icg.filtershop.model.tools;

import java.awt.image.BufferedImage;

public class WatercolorTool implements Tool {

    private final Tool tool;

    public WatercolorTool() {
        tool = new ToolChain()
                .append(new MedianSmoothTool(5))
                .append(new SharpnessTool());
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        tool.applyTo(original, result);
    }

}
