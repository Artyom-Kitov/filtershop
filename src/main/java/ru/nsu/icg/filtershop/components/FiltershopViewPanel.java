package ru.nsu.icg.filtershop.components;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
public class FiltershopViewPanel extends JPanel {
    @Getter
    private BufferedImage image = null;

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(image, 0, 0, null);
    }

    public FiltershopViewPanel() {
        setBorder(new DottedBorder(Color.BLACK, 1, 5));
        setBackground(new Color(0xAAAAFF));
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }
}
