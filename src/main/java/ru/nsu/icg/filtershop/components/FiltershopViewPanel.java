package ru.nsu.icg.filtershop.components;

import ru.nsu.icg.filtershop.model.RGBMatrix;
import ru.nsu.icg.filtershop.model.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
public class FiltershopViewPanel extends JPanel {

    private final RGBMatrix matrix;

    public FiltershopViewPanel(Dimension size) {
        matrix = new RGBMatrix(size.width, size.height);
        setBorder(new DottedBorder(Color.BLACK, 1, 5));
        setDoubleBuffered(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(matrix.getEdited(), 0, 0, null);
    }

    public BufferedImage getImage() {
        return matrix.getEdited();
    }

    public void setImage(BufferedImage image) {
        matrix.setImage(image);
        Dimension newSize = new Dimension(image.getWidth(), image.getHeight());
        setSize(newSize);
        setPreferredSize(newSize);
        repaint();
    }

}
