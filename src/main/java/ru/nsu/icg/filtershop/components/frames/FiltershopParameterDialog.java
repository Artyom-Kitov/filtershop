package ru.nsu.icg.filtershop.components.frames;

import lombok.AllArgsConstructor;
import lombok.Setter;
import ru.nsu.icg.filtershop.components.ParameterPanel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/*
Author: Mikhail Sartakov
Date: 15.03.2024
*/
public class FiltershopParameterDialog extends JDialog {

    private Map<String, ParameterPanel> parameters;

    private final JButton applyButton;
    private final JButton cancelButton;

    @Setter
    private Runnable onApply;

    public FiltershopParameterDialog() {
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
    }

    public FiltershopParameterDialog addParameter(String name, float min, float max, float initial,
                                                     String warning) {
        remove(applyButton);
        remove(cancelButton);
        ParameterPanel panel = ParameterPanel.builder()
                .name(name)
                .initial(initial)
                .min(min)
                .max(max)
                .warning(warning)
                .build();
        parameters.put(name, panel);
        add(panel, BorderLayout.CENTER);
        add(applyButton);
        add(cancelButton);
        pack();
        setSize(new Dimension(panel.getWidth(), panel.getHeight() * parameters.size() + 100));
        return this;
    }

    public float getParameterValue(String parameter) {
        if (!parameters.containsKey(parameter)) {
            throw new IllegalArgumentException("no such parameter: " + parameter);
        }
        return parameters.get(parameter).getValue();
    }

}
