package ru.nsu.icg.filtershop.components.frames;

import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HelpFrame extends JDialog {

  public static final HelpFrame INSTANCE = new HelpFrame();

  private HelpFrame() {
    setIconImage(Objects.requireNonNull(
                    ImageUtils.getImageFromResources("/icons/filtershop_logo_icon.png"))
            .getImage()
    );
    setSize(300, 450);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JLabel titleLabel = new JLabel("Welcome to the FilterShop!");
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JTextArea instructionArea = new JTextArea();
    instructionArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
    instructionArea.setText(
            """
                    FilterShop is an image processing application that\s
                    allows you to apply various filters to images,\s
                    resize and rotate images, save and load photos.

                    Available Filters:
                        - Black and White
                        - Inversion
                        - Gamma Correction
                        - Gaussian Blur
                        - Embossing
                        - Median Smooth
                        - Roberts Border Highlight
                        - Sobel Border Highlight
                        - Increase Sharpness
                        - Watercolor
                        - Pixel Art
                        - Waves
                        - Floyd-Steinberg Dithering
                        - Ordered Dithering

                    Feel free to explore our app!
                    """
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
    setVisible(false);
  }

}
