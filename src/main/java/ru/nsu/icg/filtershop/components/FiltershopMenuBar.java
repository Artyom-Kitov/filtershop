package ru.nsu.icg.filtershop.components;

import ru.nsu.icg.filtershop.components.frames.AboutFrame;
import ru.nsu.icg.filtershop.components.frames.FiltershopFrame;
import ru.nsu.icg.filtershop.components.frames.HelpFrame;

import javax.swing.*;
import java.awt.image.BufferedImage;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
public class FiltershopMenuBar extends JMenuBar {

    private final FiltershopFrame frame;
    private final FiltershopViewPanel viewPanel;
    private final JMenu file;
    private final JMenu modify;
    private final JMenu about;


    public FiltershopMenuBar(FiltershopViewPanel panel, FiltershopFrame frame) {
        this.frame = frame;
        viewPanel = panel;
        file = new JMenu("File");
        modify = new JMenu("Modify");
        about = new JMenu("About");

        configureAboutMenu();
        configureFileMenu();

        add(file);
        add(modify);
        add(about);
    }

    private void configureAboutMenu() {
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem aboutItem = new JMenuItem("About");

        helpItem.addActionListener(e -> new HelpFrame());
        aboutItem.addActionListener(e -> new AboutFrame());

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
