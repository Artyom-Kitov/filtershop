package ru.nsu.icg.filtershop.components.frames;

import lombok.Setter;
import ru.nsu.icg.filtershop.components.ParameterPanel;
import ru.nsu.icg.filtershop.components.Parameters;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/*
Author: Mikhail Sartakov
Date: 15.03.2024
*/
public class FiltershopParameterDialog extends JDialog {

    private final Map<String, ParameterPanel> parameters;

    private final JButton applyButton;
    private final JButton cancelButton;

    @Setter
    private transient Runnable onApply;

    public FiltershopParameterDialog() {
        setTitle("Parameters");

        parameters = new HashMap<>();

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setVisible(false);

        applyButton = new JButton("Apply");
        applyButton.addActionListener(e -> {
            dispose();
            onApply.run();
        });
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));

        setLocationRelativeTo(null);
    }

    public void addParameter(Parameters params) {
        remove(applyButton);
        remove(cancelButton);

        ParameterPanel panel = new ParameterPanel(params);
        parameters.put(params.name(), panel);

        add(panel, BorderLayout.CENTER);
        add(applyButton);
        add(cancelButton);

        pack();
        setSize(new Dimension(panel.getWidth(), panel.getHeight() * parameters.size() + 100));
    }

    public float getParameterValue(String parameter) {
        if (!parameters.containsKey(parameter)) {
            throw new IllegalArgumentException("no such parameter: " + parameter);
        }
        return parameters.get(parameter).getValue();
    }

}
