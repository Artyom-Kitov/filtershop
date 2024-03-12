package ru.nsu.icg.filtershop.components;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
public class FiltershopMenuBar extends JMenuBar {
    private FiltershopViewPanel viewPanel;
    private JMenu file;
    private JMenu modify;
    private JMenu filter;
    private JMenu rendering;
    private JMenu about;

    public FiltershopMenuBar(FiltershopViewPanel panel) {
        viewPanel = panel;
        file = new JMenu("File");
        modify = new JMenu("Modify");
        filter = new JMenu("Filter");
        rendering = new JMenu("Rendering");
        about = new JMenu("About");

        configureAboutMenu();
        configureFileMenu();

        add(file);
        add(modify);
        add(filter);
        add(rendering);
        add(about);
    }

    private void configureAboutMenu() {
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem aboutItem = new JMenuItem("About");

        helpItem.addActionListener(e -> new AboutFrame());
        aboutItem.addActionListener(e -> new HelpFrame());

        about.add(helpItem);
        about.add(aboutItem);
    }

    private void configureFileMenu() {
        JMenuItem importImage = new JMenuItem("Import");
        JMenuItem exportImage = new JMenuItem("Export as PNG");
        JMenuItem exit = new JMenuItem("Exit");

        importImage.addActionListener(e -> importImage());
        exportImage.addActionListener(e -> exportImage());
        exit.addActionListener(e -> System.exit(0));

        file.add(importImage);
        file.add(exportImage);
        file.add(exit);
    }

    private void importImage() {
        BufferedImage importedImage = FileManager.getInstance().importImage();
        if (importedImage != null) {
            viewPanel.setImage(importedImage);
        }
    }

    private void exportImage() {
        FileManager.getInstance().exportImageAsPNG(viewPanel.getImage());
    }

}
