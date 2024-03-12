package ru.nsu.icg.filtershop.components;

import javax.swing.*;
import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.model.RGBMatrix;
import ru.nsu.icg.filtershop.model.tools.*;


@AllArgsConstructor
public class GammaFrame extends JDialog {

  private final GammaTool gammaTool;

  private final JSlider gammaRSlider;
  private final JSlider gammaGSlider;
  private final JSlider gammaBSlider;

  public GammaFrame(RGBMatrix matrix) {
    gammaTool = new GammaTool(1, 1, 1);

    setSize(500, 240);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JPanel settingsPanel = new JPanel();
    GroupLayout layout = new GroupLayout(settingsPanel);
    settingsPanel.setLayout(layout);

    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);

    GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
    GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

    JLabel gammaRLabel = new JLabel("Gamma R:");
    JLabel gammaGLabel = new JLabel("Gamma G:");
    JLabel gammaBLabel = new JLabel("Gamma B:");

    gammaRSlider = createSlider(0.1f, 10.0f, gammaTool.getGammaR());
    gammaGSlider = createSlider(0.1f, 10.0f, gammaTool.getGammaG());
    gammaBSlider = createSlider(0.1f, 10.0f, gammaTool.getGammaB());

    JTextField gammaRTextField = createTextField(gammaRSlider);
    JTextField gammaGTextField = createTextField(gammaGSlider);
    JTextField gammaBTextField = createTextField(gammaBSlider);


    JButton okButton = new JButton("Ok");
    JButton cancelButton = new JButton("Cancel");

    okButton.addActionListener(e -> {
      float newGammaR = (float) gammaRSlider.getValue() / 10;
      float newGammaG = (float) gammaGSlider.getValue() / 10;
      float newGammaB = (float) gammaBSlider.getValue() / 10;

      gammaTool.setGammaR(newGammaR);
      gammaTool.setGammaG(newGammaG);
      gammaTool.setGammaB(newGammaB);
      gammaTool.applyTo(matrix.getResized(), matrix.getEdited());
      dispose();
    });

    cancelButton.addActionListener(e -> dispose());

    hGroup.addGroup(layout.createParallelGroup()
            .addComponent(gammaRLabel)
            .addComponent(gammaGLabel)
            .addComponent(gammaBLabel)
            .addComponent(okButton)
    );


    hGroup.addGroup(layout.createParallelGroup()
            .addComponent(gammaRSlider)
            .addComponent(gammaGSlider)
            .addComponent(gammaBSlider)
            .addComponent(cancelButton)
    );

    hGroup.addGroup(layout.createParallelGroup()
            .addComponent(gammaRTextField)
            .addComponent(gammaGTextField)
            .addComponent(gammaBTextField)
    );

    layout.setHorizontalGroup(hGroup);

    vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(gammaRLabel)
            .addComponent(gammaRSlider)
            .addComponent(gammaRTextField)
    );

    vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(gammaGLabel)
            .addComponent(gammaGSlider)
            .addComponent(gammaGTextField)
    );

    vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(gammaBLabel)
            .addComponent(gammaBSlider)
            .addComponent(gammaBTextField)
    );

    vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(okButton)
            .addComponent(cancelButton)
    );


    layout.setVerticalGroup(vGroup);

    add(settingsPanel);

    setResizable(false);
    setAlwaysOnTop(true);
    setModal(true);

    setTitle("Gamma Settings");
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private JSlider createSlider(float min, float max, float value) {
    int intMin = (int) (min * 10);
    int intMax = (int) (max * 10);
    int intValue = (int) (value * 10);
    JSlider slider = new JSlider(intMin, intMax, intValue);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    return slider;
  }


  private JTextField createTextField(JSlider slider) {
    JTextField textField = new JTextField(5);
    textField.setText(String.format("%.1f", slider.getValue() / 10.0f));
    slider.addChangeListener(e -> textField.setText(String.format("%.1f", slider.getValue() / 10.0f)));
    textField.addActionListener(e -> {
      try {
        float value = Float.parseFloat(textField.getText());
        if (value >= slider.getMinimum() / 10.0f && value <= slider.getMaximum() / 10.0f) {
          slider.setValue((int) (value * 10));
        } else {
          textField.setText(String.format("%.1f", slider.getValue() / 10.0f));
        }
      } catch (NumberFormatException ex) {
        textField.setText(String.format("%.1f", slider.getValue() / 10.0f));
      }
    });
    return textField;
  }
}
