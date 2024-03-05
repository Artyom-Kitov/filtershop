package ru.nsu.icg.filtershop;

import javax.swing.*;
import java.awt.*;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
public class FiltershopFrame extends JFrame {
    private static final Dimension MINIMUM_SIZE = new Dimension(640, 480);
    private static final Dimension INITIAL_SIZE = new Dimension(1280, 720);
    private static final int HORIZONTAL_BORDER_LAYOUT_GAP = 4;
    private static final int VERTICAL_BORDER_LAYOUT_GAP = 4;

    private final FiltershopMenuBar menuBar;
    private final FiltershopToolBar toolBar;
    private final FiltershopViewPanel imageViewWindow;

    public FiltershopFrame() {
        setDefaultParameters();

        imageViewWindow = new FiltershopViewPanel();
        toolBar = new FiltershopToolBar();
        menuBar = new FiltershopMenuBar(imageViewWindow);


        add(imageViewWindow, BorderLayout.CENTER);
        add(toolBar, BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(0, 0)), BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(0, 0)), BorderLayout.EAST);
        add(Box.createRigidArea(new Dimension(0, 0)), BorderLayout.SOUTH);
        setJMenuBar(menuBar);

        pack();
        setVisible(true);
    }

    private void setDefaultParameters() {
        setMinimumSize(MINIMUM_SIZE);
        setSize(INITIAL_SIZE);
        setPreferredSize(INITIAL_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(HORIZONTAL_BORDER_LAYOUT_GAP, VERTICAL_BORDER_LAYOUT_GAP));
    }
}
