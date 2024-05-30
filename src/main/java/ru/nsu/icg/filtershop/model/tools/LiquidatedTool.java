package ru.nsu.icg.filtershop.model.tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LiquidatedTool implements Tool {
    private final Tool blackWhiteTool = new BlackWhiteTool();
    private static final String TEXT;

    static {
        try {
            TEXT = new String(Objects.requireNonNull(LiquidatedTool.class.getResourceAsStream("/title.txt")).readAllBytes());
        } catch (IOException e) {
            throw new IllegalStateException("unable to read text", e);
        }
    }

    @Override
    public void applyTo(BufferedImage original, BufferedImage result) {
        blackWhiteTool.applyTo(original, result);

        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.RED);

        int width = result.getWidth();
        int height = result.getHeight();
        double diagonal = Math.sqrt((double) width * width + height * height);

        int fontSize = (int) (diagonal / TEXT.length() * 1.2);
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g2d.setFont(font);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(TEXT);
        int textHeight = fm.getHeight();
        double angle = Math.atan2(result.getHeight(), result.getWidth());

        g2d.rotate(-angle, width / 2., height / 2.);
        g2d.drawString(TEXT, (result.getWidth() - textWidth) / 2, (result.getHeight() + textHeight / 2) / 2);

        g2d.dispose();
    }
}
