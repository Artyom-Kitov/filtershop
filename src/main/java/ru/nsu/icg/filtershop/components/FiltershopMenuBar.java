package ru.nsu.icg.filtershop.components;

import ru.nsu.icg.filtershop.components.frames.AboutFrame;
import ru.nsu.icg.filtershop.components.frames.HelpFrame;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */

public class FiltershopMenuBar extends JMenuBar {
    private final Dimension ICON_SIZE = new Dimension(16, 16);

    private final FiltershopViewPanel viewPanel;
    private final JMenu file;
    private final JMenu info;

    public FiltershopMenuBar(FiltershopViewPanel panel) {
        viewPanel = panel;
        file = new JMenu("File");
        info = new JMenu("Info");

        configureAboutMenu();
        configureFileMenu();

        add(file);
        add(info);
    }

    private void configureAboutMenu() {
        JMenuItem helpItem = new JMenuItem("Help");
        JMenuItem aboutItem = new JMenuItem("About");

        setScaledIcon(helpItem, "/icons/info_icon.png");
        setScaledIcon(aboutItem, "/icons/info_icon.png");

        helpItem.addActionListener(e -> HelpFrame.INSTANCE.setVisible(true));
        aboutItem.addActionListener(e -> AboutFrame.INSTANCE.setVisible(true));

        info.add(helpItem);
        info.add(aboutItem);
    }

    private void configureFileMenu() {
        JMenuItem importImage = new JMenuItem("Import");
        JMenuItem exportImage = new JMenuItem("Export as PNG");
        JMenuItem exit = new JMenuItem("Exit");

        setScaledIcon(importImage, "/icons/import_image_icon.png");
        setScaledIcon(exportImage, "/icons/export_image_icon.png");
        setScaledIcon(exit, "/icons/exit_button_icon.png");

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

    private void setScaledIcon(JMenuItem item, String path) {
        item.setIcon(ImageUtils.getScaledImageFromResources(
           path,
           ICON_SIZE.width,
           ICON_SIZE.height
        ));
    }
}
