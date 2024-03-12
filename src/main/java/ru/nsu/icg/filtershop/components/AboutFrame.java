package ru.nsu.icg.filtershop.components;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JDialog {

  public AboutFrame() {
    setSize(300, 100);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JLabel titleLabel = new JLabel("FilterShop v1.0");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JLabel authorLabel = new JLabel("Authors: Artyom Kitov, " +
                                                  "Mikhail Sartakov, " +
                                                  "Anton Nazarov");
    authorLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> dispose());

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(titleLabel, BorderLayout.NORTH);
    panel.add(authorLabel, BorderLayout.CENTER);
    panel.add(closeButton, BorderLayout.SOUTH);

    add(panel);

    setTitle("About");
    setResizable(false);
    setLocationRelativeTo(null);
    setAlwaysOnTop(true);
    setModal(true);
    setVisible(true);
  }

}
