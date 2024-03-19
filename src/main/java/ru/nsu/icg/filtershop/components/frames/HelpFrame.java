package ru.nsu.icg.filtershop.components.frames;

import javax.swing.*;
import java.awt.*;

public class HelpFrame extends JDialog {

  public HelpFrame() {
    setSize(500, 230);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JLabel titleLabel = new JLabel("Welcome to the FilterShop!");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JTextArea instructionArea = new JTextArea();
    instructionArea.setText(
            """
                    This application, FilterShop, provides the following features:
                   
                    - Apply various filters listed in the toolbar to enhance your images.
                    - Save and load photos to work on them later.
                    - Resize and rotate images to fit your preferences.
                    
                    Feel free to explore and create amazing images!"""
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

    setTitle("Help");
    setResizable(false);
    setLocationRelativeTo(null);
    setAlwaysOnTop(true);
    setModal(true);
    setVisible(true);
  }

}
