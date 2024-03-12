package ru.nsu.icg.filtershop.components;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {

  public AboutFrame() {
//    super(parent, "About", true);
    setSize(300, 100);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JLabel titleLabel = new JLabel("PaintPlant v1.0");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JLabel authorLabel = new JLabel("Author: Anton Nazarov");
    authorLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> dispose());

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(authorLabel, BorderLayout.CENTER);
    panel.add(closeButton, BorderLayout.SOUTH);

    add(panel);

    setVisible(true);
  }

}
