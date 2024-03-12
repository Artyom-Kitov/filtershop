package ru.nsu.icg.filtershop.components;

import ru.nsu.icg.filtershop.model.tools.BlackWhiteTool;
import ru.nsu.icg.filtershop.model.tools.InversionTool;

import javax.swing.*;
import java.awt.*;
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
    private JMenu help;

    public FiltershopMenuBar(FiltershopViewPanel panel) {
        viewPanel = panel;
        file = new JMenu("File");
        modify = new JMenu("Modify");
        filter = new JMenu("Filter");
        rendering = new JMenu("Rendering");
        help = new JMenu("Help");

        configureFileMenu();
        configureFilterMenu();

        add(file);
        add(modify);
        add(filter);
        add(rendering);
        add(help);
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

    private void configureFilterMenu() {
        JMenuItem blackWhite = new JMenuItem("Black and white");
        JMenuItem inversion = new JMenuItem("Color inversion");

        inversion.addActionListener(e -> {
            viewPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            new InversionTool().applyTo(viewPanel.getMatrix());
            viewPanel.repaint();
            viewPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        });
        blackWhite.addActionListener(e -> {
            viewPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            new BlackWhiteTool().applyTo(viewPanel.getMatrix());
            viewPanel.repaint();
            viewPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        });

        filter.add(blackWhite);
        filter.add(inversion);
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
