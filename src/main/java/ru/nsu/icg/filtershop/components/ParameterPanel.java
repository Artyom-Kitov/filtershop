package ru.nsu.icg.filtershop.components;

import javax.swing.*;
import java.awt.*;

/*
Author: Mikhail Sartakov
Date: 15.03.2024
*/
public class ParameterPanel extends JPanel {

    private final int coefficient;

    private final JSlider parameterSlider;

    public ParameterPanel(Parameters params) {
        coefficient = countCoefficient(params.min(), params.max());

        JLabel parameterLabel = new JLabel(params.name());
        parameterSlider = createToolSettingsSlider(params.min(), params.max(), params.initial(), params.step());
        if (params.step() != null) {
            parameterSlider.addChangeListener(e -> {
                int value = parameterSlider.getValue();
                int newValue = (int) params.min();
                while (newValue < value) {
                    newValue += params.step();
                }
                parameterSlider.setValue(newValue);
            });
        }
        JTextField parameterTextField = createToolSettingsTextField(parameterSlider, String.valueOf(params.initial()));

        add(parameterLabel);
        add(parameterSlider);
        add(parameterTextField);
        setPreferredSize(new Dimension(380, 60));
    }

    public float getValue() {
        return (float) parameterSlider.getValue() / coefficient;
    }

    private JSlider createToolSettingsSlider(float min, float max, float current, Float step) {
        int intMin = (int) (min * coefficient);
        int intMax = (int) (max * coefficient);
        int intCurrent = (int) (current * coefficient);

        JSlider slider = new JSlider(intMin, intMax, intCurrent);
        if (step == null) {
            slider.setMajorTickSpacing(1);
        } else {
            slider.setMajorTickSpacing((int) step.floatValue());
        }
        slider.setPaintTicks(true);

        return slider;
    }

    private JTextField createToolSettingsTextField(JSlider slider, String warningMessage) {
        JTextField textField = new JTextField(4);
        setTextFieldValue(getSliderValue(slider.getValue()), textField);
        slider.addChangeListener(e -> {
            setTextFieldValue(getSliderValue(slider.getValue()), textField);
            slider.setValue((int) (Float.parseFloat(textField.getText()) * coefficient));
        });
        textField.addActionListener(e -> trySetSliderValueFromText(slider, textField, warningMessage));
        return textField;
    }

    private void trySetSliderValueFromText(JSlider slider, JTextField textField, String warningMessage) {
        try {
            float value = Float.parseFloat(textField.getText());
            if (value * coefficient >= slider.getMinimum() && value * coefficient <= slider.getMaximum()) {
                slider.setValue((int) (value * coefficient));
            } else {
                setTextFieldValue(getSliderValue(slider.getValue()), textField);
                JOptionPane.showMessageDialog(this, warningMessage,
                        "Warning!", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, warningMessage, "Warning!", JOptionPane.WARNING_MESSAGE);
            textField.setText(Float.toString((float) slider.getValue() / coefficient));
        }
    }

    private void setTextFieldValue(float value, JTextField textField) {
        String text;
        if (value - (int) value > 0f) {
            text = Float.toString(value);
        } else {
            text = Integer.toString((int) value);
        }
        textField.setText(text);
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

    private float getSliderValue(int value) {
        return (float) value / coefficient;
    }

}
