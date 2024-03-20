package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import ru.nsu.icg.filtershop.model.RGBMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


/*
Author: Mikhail Sartakov
Date: 06.03.2024
*/
@Getter
public class FiltershopViewPanel extends JPanel {
    private Object interpolationType;
    private DisplayMode displayMode = DisplayMode.FULL_SIZE;

    private final RGBMatrix matrix;

    public FiltershopViewPanel(Dimension size) {
        matrix = new RGBMatrix(size.width, size.height);
        interpolationType = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
        setBorder(new DottedBorder(Color.BLACK, 1, 5));
        setDoubleBuffered(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        BufferedImage image = matrix.getResult();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, interpolationType);
        switch (displayMode) {
            case FULL_SIZE -> {
                int x = getWidth() > image.getWidth() ? (getWidth() - image.getWidth()) / 2 : 0;
                int y = getHeight() > image.getHeight() ? (getHeight() - image.getHeight()) / 2 : 0;
                g2.drawImage(image, x, y, null);
            }
            case FIT_TO_SCREEN_SIZE -> {
                float factor = findScaleFactor(image.getWidth(), image.getHeight(), getWidth(), getHeight());
                int newWidth = (int) (factor * image.getWidth());
                int newHeight = (int) (factor * image.getHeight());
                Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                int x = getWidth() > scaledImage.getWidth(null) ? (getWidth() - scaledImage.getWidth(null)) / 2 : 0;
                int y = getHeight() > scaledImage.getHeight(null) ? (getHeight() - scaledImage.getHeight(null)) / 2 : 0;
                g2.drawImage(scaledImage, x, y, null);
            }
        }
    }

    public BufferedImage getImage() {
        return matrix.getRotatedFiltered();
    }

    public void setImage(BufferedImage image) {
        matrix.setImage(image);
        if (displayMode == DisplayMode.FULL_SIZE) {
            resizePanelToImage();
        }
        repaint();
    }

    public void setDisplayMode(DisplayMode displayMode) {
        this.displayMode = displayMode;
        if (displayMode == DisplayMode.FULL_SIZE) {
            resizePanelToImage();
        }
    }

    private float findScaleFactor(int imageWidth, int imageHeight, int panelWidth, int panelHeight) {
        float scaleX = (float) panelWidth / imageWidth;
        float scaleY = (float) panelHeight / imageHeight;
        return Math.min(scaleX, scaleY);
    }

    public void resizePanelToImage() {
        Dimension newSize = new Dimension(matrix.getRotatedFiltered().getWidth(), matrix.getRotatedFiltered().getHeight());
        setSize(newSize);
        setPreferredSize(newSize);
    }
}
