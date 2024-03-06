package ru.nsu.icg.filtershop.components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
public class FiltershopMenuBar extends JMenuBar implements ActionListener {
    private FiltershopViewPanel viewPanel;
    private JMenu file;
    private JMenu modify;
    private JMenu filter;
    private JMenu rendering;
    private JMenu help;
    public FiltershopMenuBar(FiltershopViewPanel panel) {
        viewPanel = panel;
        file = new JMenu("File");
        modify = new JMenu("Modify");
        filter = new JMenu("Filter");
        rendering = new JMenu("Rendering");
        help = new JMenu("Help");

        configureFileMenu();

        add(file);
        add(modify);
        add(filter);
        add(rendering);
        add(help);
    }

    private void configureFileMenu() {
        JMenuItem importImage = new JMenuItem("Import an image");
        JMenuItem exportImage = new JMenuItem("Export an image as PNG");
        JMenuItem exit = new JMenuItem("Exit");

        importImage.addActionListener(this);
        exportImage.addActionListener(this);
        exit.addActionListener(this);

        file.add(importImage);
        file.add(exportImage);
        file.add(exit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()) {
            case "Import an image" -> {
                BufferedImage importedImage = FileManager.getInstance().importImage();
                if (importedImage != null) {
                    viewPanel.setImage(importedImage);
                }
            }
            case "Export an image as PNG" -> FileManager.getInstance().exportImageAsPNG(viewPanel.getImage());
        }
    }
}
