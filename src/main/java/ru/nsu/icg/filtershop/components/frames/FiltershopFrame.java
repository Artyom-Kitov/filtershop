package ru.nsu.icg.filtershop.components.frames;

import ru.nsu.icg.filtershop.components.*;
import ru.nsu.icg.filtershop.components.DisplayMode;
import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/*
Author: Mikhail Sartakov
Date: 06.03.2024
 */
public class FiltershopFrame extends JFrame {
    private static final Dimension MINIMUM_SIZE = new Dimension(640, 480);
    private static final Dimension INITIAL_SIZE = new Dimension(1280, 720);
    private static final int HORIZONTAL_BORDER_LAYOUT_GAP = 4;
    private static final int VERTICAL_BORDER_LAYOUT_GAP = 4;
    private static final String[] INTERPOLATION_TYPES_NAMES = {
            "Bilinear interpolation",
            "Bicubic interpolation",
            "Nearest neighbor interpolation"
    };

    private final FiltershopMenuBar menuBar;
    private final FiltershopToolBar toolBar;
    private final FiltershopViewPanel imageViewWindow;
    private final JScrollPane scrollPane;

    private final DisplayModesList displayModesList;
    private final FiltersList filtersList;

    private Point origin;

    public FiltershopFrame() {
        setDefaultParameters();
        setTitle("Filtershop");
        setIconImage(Objects.requireNonNull(
                        ImageUtils.getImageFromResources("/icons/filtershop_logo_icon.png"))
                        .getImage()
        );

        imageViewWindow = new FiltershopViewPanel(INITIAL_SIZE);
        toolBar = new FiltershopToolBar();
        menuBar = new FiltershopMenuBar(imageViewWindow);

        toolBar.add(createToolBarButton("/icons/import_image_icon.png", "Import", () -> {
            BufferedImage importedImage = FileManager.getInstance().importImage();
            if (importedImage != null) {
                imageViewWindow.setImage(importedImage);
            }
        }));
        toolBar.add(createToolBarButton("/icons/export_image_icon.png", "Export",
                () -> FileManager.getInstance().exportImageAsPNG(imageViewWindow.getImage())));
        toolBar.add(createToolBarButton("/icons/exit_button_icon.png", "Exit",
                () -> System.exit(0)));
        toolBar.addSeparator();

        filtersList = new FiltersList(this::onToolSelect, this::onReset, toolBar);
        toolBar.addSeparator();
        displayModesList = new DisplayModesList(this::onDisplayModeSelect, angle -> {
            imageViewWindow.getMatrix().setRotatingAngle(angle);
            imageViewWindow.repaint();
        }, toolBar);

        JComboBox<String> interpolationComboBox = new JComboBox<>(INTERPOLATION_TYPES_NAMES);
        interpolationComboBox.setMaximumSize(new Dimension(192, 32));
        interpolationComboBox.addActionListener(this::onInterpolationTypeSelect);
        toolBar.add(interpolationComboBox);

        scrollPane = new JScrollPane(imageViewWindow);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().setBorder(null);
        scrollPane.getHorizontalScrollBar().setBorder(null);
        scrollPane.getViewport().setView(imageViewWindow);
        configureMouseDragListener();

        add(scrollPane, BorderLayout.CENTER);
        add(toolBar, BorderLayout.NORTH);
        add(Box.createRigidArea(new Dimension(0, 0)), BorderLayout.WEST);
        add(Box.createRigidArea(new Dimension(0, 0)), BorderLayout.EAST);
        add(Box.createRigidArea(new Dimension(0, 0)), BorderLayout.SOUTH);

        menuBar.add(filtersList.getToolsMenu());
        menuBar.add(displayModesList.getDisplayModesMenu());
        setJMenuBar(menuBar);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                onResize();
            }
        });

        pack();
        setVisible(true);
    }

    private JButton createToolBarButton(String iconPath, String toolTip, Runnable onClick) {
        JButton button = new JButton();
        button.setSize(32, 32);
        button.setToolTipText(toolTip);
        button.setIcon(ImageUtils.getScaledImageFromResources(iconPath, 32, 32));
        button.addActionListener(e -> onClick.run());
        return button;
    }

    private void onResize() {
        if (imageViewWindow.getDisplayMode() == DisplayMode.FIT_TO_SCREEN_SIZE) {
            int width = getWidth() - 100;
            int height = getHeight() - 200;
            imageViewWindow.setSize(width, height);
            imageViewWindow.setPreferredSize(new Dimension(width, height));
        }
    }

    private void onToolSelect(Tool tool) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        imageViewWindow.getMatrix().applyTool(tool);
        imageViewWindow.repaint();
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    private void onDisplayModeSelect(DisplayMode mode) {
        imageViewWindow.setDisplayMode(mode);
        onResize();
        repaint();
    }

    private void onReset() {
        imageViewWindow.getMatrix().swap();
        repaint();
    }

    private void setDefaultParameters() {
        setMinimumSize(MINIMUM_SIZE);
        setSize(INITIAL_SIZE);
        setPreferredSize(INITIAL_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(HORIZONTAL_BORDER_LAYOUT_GAP, VERTICAL_BORDER_LAYOUT_GAP));
    }

    private void configureMouseDragListener() {
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imageViewWindow.getMatrix().swap();
                filtersList.setLastSelected(!imageViewWindow.getMatrix().isSwapped());
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (imageViewWindow.getDisplayMode() == DisplayMode.FULL_SIZE) {
                    origin = e.getPoint();
                    setDragCursor();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                if (imageViewWindow.getDisplayMode() == DisplayMode.FULL_SIZE) {
                    setDefaultCursor();
                }
            }
        });
        scrollPane.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (imageViewWindow.getDisplayMode() == DisplayMode.FULL_SIZE) {
                    int horizontalShift = e.getX() - origin.x;
                    int verticalShift = e.getY() - origin.y;
                    scrollPane.getVerticalScrollBar().setValue(
                            scrollPane.getVerticalScrollBar().getValue() - verticalShift
                    );
                    scrollPane.getHorizontalScrollBar().setValue(
                            scrollPane.getHorizontalScrollBar().getValue() - horizontalShift
                    );

                    origin = e.getPoint();
                }
            }
        });
    }

    private void setDragCursor() {
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    private void setDefaultCursor() {
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    private void onInterpolationTypeSelect(ActionEvent e) {
        JComboBox<String> cb = (JComboBox<String>) e.getSource();
        String selectedItem = (String) cb.getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        switch (selectedItem) {
            case "Bilinear interpolation" -> imageViewWindow.setInterpolationType(RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            case "Bicubic interpolation" -> imageViewWindow.setInterpolationType(RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            case "Nearest neighbor interpolation" -> imageViewWindow.setInterpolationType(RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        }
    }
}
