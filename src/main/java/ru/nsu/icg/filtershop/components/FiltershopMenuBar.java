package ru.nsu.icg.filtershop.components;

import ru.nsu.icg.filtershop.model.tools.BlackWhiteTool;
import ru.nsu.icg.filtershop.model.tools.InversionTool;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
public class FiltershopMenuBar extends JMenuBar {

    private FiltershopFrame frame;
    private final FiltershopViewPanel viewPanel;
    private final JMenu file;
    private final JMenu modify;
    private final JMenu filter;
    private final JMenu rendering;
    private final JMenu about;


    public FiltershopMenuBar(FiltershopViewPanel panel, FiltershopFrame frame) {
        this.frame = frame;
        viewPanel = panel;
        file = new JMenu("File");
        modify = new JMenu("Modify");
        filter = new JMenu("Filter");
        rendering = new JMenu("Rendering");
        about = new JMenu("About");

        configureAboutMenu();
        configureFileMenu();
        configureFilterMenu();

        add(file);
        add(modify);
        add(filter);
        add(rendering);
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

    private void configureFilterMenu() {
        JMenuItem blackWhite = new JMenuItem("Black and white");
        JMenuItem inversion = new JMenuItem("Color inversion");
        JMenuItem gammaCorrection = new JMenuItem("Gamma Correction");

        inversion.addActionListener(e -> onClick(() -> viewPanel.getMatrix().applyTool(new InversionTool())));
        blackWhite.addActionListener(e -> onClick(() -> viewPanel.getMatrix().applyTool(new BlackWhiteTool())));
        gammaCorrection.addActionListener(e -> onClick(() -> new GammaFrame(viewPanel.getMatrix())));

        filter.add(blackWhite);
        filter.add(inversion);
        filter.add(gammaCorrection);
    }

    private void onClick(Runnable r) {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        r.run();
        viewPanel.repaint();
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
