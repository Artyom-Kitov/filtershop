package ru.nsu.icg.filtershop.components;

import lombok.Builder;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/*
Author: Mikhail Sartakov
Date: 15.03.2024
*/

public class ParameterPanel extends JPanel {

    private final int coefficient;

    private final JLabel parameterLabel;
    private final JSlider parameterSlider;
    private final JTextField parameterTextField;

    @Builder
    public ParameterPanel(float min, float max, float initial, String name, String warning) {
        coefficient = countCoefficient(min, max);

        parameterLabel = new JLabel(name);
        parameterSlider = createToolSettingsSlider(min, max, initial);
        parameterTextField = createToolSettingsTextField(parameterSlider, warning);

        add(parameterLabel);
        add(parameterSlider);
        add(parameterTextField);
        setPreferredSize(new Dimension(340, 60));
    }

    public float getValue() {
        return (float) parameterSlider.getValue() / coefficient;
    }

    private JSlider createToolSettingsSlider(float min, float max, float current) {
        int intMin = (int) (min * coefficient);
        int intMax = (int) (max * coefficient);
        int intCurrent = (int) (current * coefficient);

        JSlider slider = new JSlider(intMin, intMax, intCurrent);
        slider.setMajorTickSpacing((intMax - intMin) / 2);
        slider.setPaintTicks(true);

        return slider;
    }

    private JTextField createToolSettingsTextField(JSlider slider, String warningMessage) {
        JTextField textField = new JTextField(4);
        textField.setText(getSliderValue(slider.getValue()));
        slider.addChangeListener(e -> textField.setText(getSliderValue(slider.getValue())));
        textField.addActionListener(e -> trySetSliderValueFromText(slider, textField, warningMessage));
        return textField;
    }

    private void trySetSliderValueFromText(JSlider slider, JTextField textField, String warningMessage) {
        try {
            float value = Float.parseFloat(textField.getText());
            if (value * coefficient >= slider.getMinimum() && value * coefficient <= slider.getMaximum()) {
                slider.setValue((int) (value * coefficient));
            } else {
                textField.setText(Float.toString((float) slider.getValue() / coefficient));
                JOptionPane.showMessageDialog(this, warningMessage,
                        "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, warningMessage, "Warning!", JOptionPane.WARNING_MESSAGE);
            textField.setText(Float.toString((float) slider.getValue() / coefficient));
        }
    }

    private int countCoefficient(float lowerBound, float higherBound) {
        int higherBoundCoeff = 1;
        double absoluteMaxValue = Math.abs(higherBound);
        while ((int) Math.floor(absoluteMaxValue) < 1) {
            higherBoundCoeff *= 10;
            absoluteMaxValue *= 10;
        }

        int lowerBoundCoeff = 1;
        double absoluteMinValue = Math.abs(lowerBound);
        while ((int) Math.floor(absoluteMinValue) < 1 && absoluteMinValue != 0) {
            lowerBoundCoeff *= 10;
            absoluteMinValue *= 10;
        }
        return Math.max(higherBoundCoeff, lowerBoundCoeff);
    }

    private String getSliderValue(int value) {
        double trueValue = (double) value / coefficient;
        return Double.toString(trueValue);
    }

}
