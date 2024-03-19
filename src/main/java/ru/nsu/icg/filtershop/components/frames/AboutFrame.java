package ru.nsu.icg.filtershop.components.frames;

import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class AboutFrame extends JDialog {

  public static final AboutFrame INSTANCE = new AboutFrame();

  private AboutFrame() {
    setIconImage(Objects.requireNonNull(
                    ImageUtils.getImageFromResources("/icons/filtershop_logo_icon.png"))
            .getImage()
    );
    setSize(300, 100); // create constants
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JLabel titleLabel = new JLabel("FilterShop v1.0"); // create constant
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // create constant
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JLabel authorLabel = new JLabel("Authors: Artyom Kitov, " + // create constant
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
    setVisible(false);
  }

}
