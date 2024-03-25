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
    setSize(300, 650);
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
                        - Black and White (Anton Nazarov)
                        - Inversion (Anton Nazarov)
                        - Gamma Correction (Anton Nazarov)
                        - Gaussian Blur (Artyom Kitov)
                        - Embossing (Anton Nazarov)
                        - Median Smooth (Anton Nazarov)
                        - Roberts Border Highlight (Artyom Kitov)
                        - Sobel Border Highlight (Artyom Kitov)
                        - Increase Sharpness (Anton Nazarov)
                        - Watercolor (Anton Nazarov)
                        - Pixel Art (Anton Nazarov)
                        - Waves (Artyom Kitov)
                        - Swirl (Mikhail Sartakov)
                        - Sepia (Mikhail Sartakov)
                        - RGB channels : (Mikhail Sartakov)
                        - Floyd-Steinberg Dithering
                        - Ordered Dithering
                        
                        
                    Rotation: Mikhail Sartakov  
                    Interpolation: Mikhail Sartakov  
                    View modes: Mikhail Sartakov  
                    System design: Artyom Kitov
                    UI: Mikhail Sartakov
                    Optimizations: Artyom Kitov
                    
                    Help and About: Anton Nazarov

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
