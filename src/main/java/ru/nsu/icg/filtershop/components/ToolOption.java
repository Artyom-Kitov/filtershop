package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.filtershop.model.tools.Tool;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

@Getter
public class ToolOption {

    private static final int BUTTON_SIZE = 32;
    private static final int MENU_BUTTON_SIZE = 16;

    @Setter
    private Tool tool;

    @Setter
    private Consumer<? super Tool> onSelect;

    private final JRadioButton radioButton;
    private final JRadioButtonMenuItem menuItem;

    public ToolOption(String name, Tool tool,
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

    public void setIcons(String defaultIconPath, String selectedIconPath) {
        menuItem.setIcon(ImageUtils.getScaledImageFromResources(defaultIconPath, MENU_BUTTON_SIZE, MENU_BUTTON_SIZE));
        radioButton.setIcon(ImageUtils.getScaledImageFromResources(defaultIconPath, BUTTON_SIZE, BUTTON_SIZE));
        radioButton.setSelectedIcon(ImageUtils.getScaledImageFromResources(selectedIconPath, BUTTON_SIZE, BUTTON_SIZE));
    }

    private void handleSelection(Consumer<? super Tool> onSelect, Runnable onCancel) {
        if (radioButton.isSelected()) {
            setSelected(false);
            onSelect.accept(tool);
        } else {
            onCancel.run();
        }
    }

    private void setSelected(boolean b) {
        radioButton.setSelected(b);
        menuItem.setSelected(b);
    }

}
