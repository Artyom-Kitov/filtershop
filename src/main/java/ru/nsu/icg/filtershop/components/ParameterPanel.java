package ru.nsu.icg.filtershop.components;

import lombok.Getter;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/*
Author: Mikhail Sartakov
Date: 15.03.2024
*/

@Getter
public class ParameterPanel<T extends Number> extends JPanel {
    private final T lowerBound;
    private final T higherBound;
    private final String parameterName;
    private final String warningMessage;
    private T currentValue;
    private final int coefficient;

    private final JLabel parameterLabel;
    private final JSlider parameterSlider;
    private final JTextField parameterTextField;

    public ParameterPanel(T cv, T lb, T hb, String name, String wm) {
        lowerBound = lb;
        higherBound = hb;
        parameterName = name;
        warningMessage = wm;
        currentValue = cv;
        coefficient = countCoefficient();

        parameterLabel = new JLabel(parameterName);
        parameterSlider = createToolSettingsSlider(lowerBound, higherBound, currentValue);
        parameterTextField = createToolSettingsTextField(parameterSlider, warningMessage);

        add(parameterLabel);
        add(parameterSlider);
        add(parameterTextField);
    }

    private JSlider createToolSettingsSlider(T min, T max, T current) {
        int intMin = toInt(min);
        int intMax = toInt(max);
        int intCurrent = toInt(current);

        JSlider slider = new JSlider(intMin, intMax, intCurrent);
        slider.setMajorTickSpacing((intMax - intMin) / 2);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        return slider;
    }

    private JTextField createToolSettingsTextField(JSlider slider, String warningMessage) {
        JTextField textField = new JTextField();
        textField.setText(toString(slider.getValue()));
        slider.addChangeListener(e -> textField.setText(toString(slider.getValue())));
        textField.addActionListener(e -> trySetSliderValueFromText(slider, textField, warningMessage));
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                trySetSliderValueFromText(slider, textField, warningMessage);
            }
        });
        return textField;
    }

    private void trySetSliderValueFromText(JSlider slider, JTextField textField, String warningMessage) {
        try {
            int value = Integer.parseInt(textField.getText());
            if (value >= slider.getMinimum() && value <= slider.getMaximum()) {
                slider.setValue(value);
            }
            else {
                JOptionPane.showMessageDialog(null, warningMessage, "Warning!", JOptionPane.WARNING_MESSAGE);
                textField.setText(Integer.toString(slider.getValue()));
            }
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, warningMessage, "Warning!", JOptionPane.WARNING_MESSAGE);
            textField.setText(Integer.toString(slider.getValue()));
        }
    }

    private int toInt(T value) {
        return (int) (value.doubleValue() * coefficient);
    }

    private int countCoefficient() {
        int higherBoundCoeff = 1;
        double absoluteMaxValue = Math.abs(higherBound.doubleValue());
        while ((int) Math.floor(absoluteMaxValue) < 1) {
            higherBoundCoeff *= 10;
            absoluteMaxValue *= 10;
        }

        int lowerBoundCoeff = 1;
        double absoluteMinValue = Math.abs(lowerBound.doubleValue());
        while ((int) Math.floor(absoluteMinValue) < 1 && absoluteMinValue != 0) {
            lowerBoundCoeff *= 10;
            absoluteMinValue *= 10;
        }
        return Math.max(higherBoundCoeff, lowerBoundCoeff);
    }

    private String toString(int value) {
        double trueValue = (double) value / coefficient;
        return Double.toString(trueValue);
    }
}
