package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import ru.nsu.icg.filtershop.model.RGBMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
@Getter
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
        BufferedImage image = matrix.getFiltered();
        int x = getWidth() > image.getWidth() ? (getWidth() - image.getWidth()) / 2 : 0;
        int y = getHeight() > image.getHeight() ? (getHeight() - image.getHeight()) / 2 : 0;
        g2.drawImage(matrix.getFiltered(), x, y, null);
    }

    public BufferedImage getImage() {
        return matrix.getFiltered();
    }

    public void setImage(BufferedImage image) {
        matrix.setImage(image);
        Dimension newSize = new Dimension(image.getWidth(), image.getHeight());
        setSize(newSize);
        setPreferredSize(newSize);
        repaint();
    }

}
