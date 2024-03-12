package ru.nsu.icg.filtershop.components;

import javax.swing.*;
import java.awt.*;

public class HelpFrame extends JFrame {

  public HelpFrame() {
    setSize(500, 230);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JLabel titleLabel = new JLabel("Welcome to the PaintPlant!");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JTextArea instructionArea = new JTextArea();
    instructionArea.setText(
            """
                    Here are some instructions on how to use the tools:
                    - Line: Click to set the starting point, then click again to set the end point of the line.
                    - Clear: Click to clear all drawing.
                    - Fill: Click inside a closed shape to fill it with the selected color.
                    - Star: Click to draw a star shape.
                    - Polygon: Click to draw a polygon shape.

                    Feel free to explore and create amazing artwork!"""
    );
    instructionArea.setEditable(false);
    instructionArea.setLineWrap(true);
    instructionArea.setWrapStyleWord(true);

    JScrollPane scrollPane = new JScrollPane(instructionArea);

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> dispose());

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);
    panel.add(closeButton, BorderLayout.SOUTH);

    add(panel);

    setVisible(true);
  }

}
