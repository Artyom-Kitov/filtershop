package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.filtershop.model.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class ToolOption {

    private static final int BUTTON_SIZE = 32;

    @Setter
    private Tool tool;

    @Getter
    private final JRadioButton radioButton;

    @Getter
    private final JRadioButtonMenuItem menuItem;

    private boolean isPreviouslySelected = false;

    public ToolOption(String name, Tool tool, Consumer<? super Tool> onSelect,
                      Runnable onCancel) {
        this.tool = tool;

        radioButton = new JRadioButton();
        radioButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        radioButton.setToolTipText(name);

        menuItem = new JRadioButtonMenuItem(name);

        radioButton.addActionListener(e -> {
            setSelected(radioButton.isSelected());
            handleSelection(onSelect, onCancel);
        });

        menuItem.addActionListener(e -> {
            setSelected(menuItem.isSelected());
            handleSelection(onSelect, onCancel);
        });
    }

    private void handleSelection(Consumer<? super Tool> onSelect, Runnable onCancel) {
        if (isPreviouslySelected && radioButton.isSelected()) {
            setSelected(false);
            onCancel.run();
        } else {
            onSelect.accept(tool);
        }
        isPreviouslySelected = radioButton.isSelected();
    }

    private void setSelected(boolean b) {
        radioButton.setSelected(b);
        menuItem.setSelected(b);
    }

}
