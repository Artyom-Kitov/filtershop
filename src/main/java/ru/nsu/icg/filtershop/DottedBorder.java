package ru.nsu.icg.filtershop;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class DottedBorder extends AbstractBorder {
    private final Color color;
    private final int thickness;
    private final int dotSize;

    public DottedBorder(Color color, int thickness, int dotSize) {
        this.color = color;
        this.thickness = thickness;
        this.dotSize = dotSize;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(thickness,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f,
                new float[]{dotSize},
                0.0f
        ));
        g2d.drawRect(x, y, width - 1, height - 1); // Subtract 1 to avoid painting outside the component bounds
        g2d.dispose();
    }
}
