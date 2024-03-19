package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

@Getter
public class DisplayModeOption {
    private static final int BUTTON_SIZE = 32;
    private static final int MENU_BUTTON_SIZE = 16;

    @Setter
    private DisplayMode displayMode;

    private final JRadioButton radioButton;
    private final JRadioButtonMenuItem menuItem;

    public DisplayModeOption(String name, Consumer<? super DisplayMode> onSelect, Runnable onCancel) {
        radioButton = new JRadioButton();
        radioButton.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        radioButton.setToolTipText(name);

        menuItem = new JRadioButtonMenuItem(name);

        radioButton.addActionListener(e -> {
            setSelected(radioButton.isSelected());
            handleSelection(onSelect, onCancel);
        });
    }

    public void setIcons(String defaultIconPath, String selectedIconPath) {
        menuItem.setIcon(ImageUtils.getScaledImageFromResources(defaultIconPath, MENU_BUTTON_SIZE, MENU_BUTTON_SIZE));
        radioButton.setIcon(ImageUtils.getScaledImageFromResources(defaultIconPath, BUTTON_SIZE, BUTTON_SIZE));
        radioButton.setSelectedIcon(ImageUtils.getScaledImageFromResources(selectedIconPath, BUTTON_SIZE, BUTTON_SIZE));
    }

    private void handleSelection(Consumer<? super DisplayMode> onSelect, Runnable onCancel) {
        if (radioButton.isSelected()) {
            onSelect.accept(displayMode);
        } else {
            onCancel.run();
        }
    }

    private void setSelected(boolean b) {
        radioButton.setSelected(b);
        menuItem.setSelected(b);
    }
}
