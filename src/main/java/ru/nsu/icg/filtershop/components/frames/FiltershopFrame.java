package ru.nsu.icg.filtershop.components.frames;

import ru.nsu.icg.filtershop.components.*;
import ru.nsu.icg.filtershop.model.tools.Tool;

import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private final JScrollPane scrollPane;

    private final FiltersList filtersList;

    private Point origin;

    public FiltershopFrame() {
        setDefaultParameters();

        filtersList = new FiltersList(this::onToolSelect, this::onReset);

        imageViewWindow = new FiltershopViewPanel(INITIAL_SIZE);
        toolBar = filtersList.getToolBar();
        menuBar = new FiltershopMenuBar(imageViewWindow, this);

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
        setJMenuBar(menuBar);

        pack();
        setVisible(true);
    }

    private void onToolSelect(Tool tool) {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        imageViewWindow.getMatrix().applyTool(tool);
        imageViewWindow.repaint();
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    private void onReset() {
        imageViewWindow.getMatrix().reset();
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
            public void mousePressed(MouseEvent e) {
                origin = e.getPoint();
                setDragCursor();
            }
        });

        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                setDefaultCursor();
            }
        });

        scrollPane.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
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
        });
    }

    private void setDragCursor() {
        setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }

    private void setDefaultCursor() {
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
}
