package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import ru.nsu.icg.filtershop.components.frames.FiltershopParameterDialog;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class DisplayModesList {
    @Getter
    private final FiltershopToolBar toolBar;

    @Getter
    private final JMenu displayModesMenu;

    private final List<DisplayModeOption> displayModes;
    private final ButtonGroup toolBarGroup;
    private final ButtonGroup menuBarGroup;

    public DisplayModesList(Consumer<? super DisplayMode> onModeSelect,
                            IntConsumer onAngleChange,
                            FiltershopToolBar mainToolBar) {
        toolBar = mainToolBar;
        displayModesMenu = new JMenu("Display");
        displayModes = new ArrayList<>();

        createFullSizeMode(onModeSelect);
        createFitToScreenMode(onModeSelect);

        toolBarGroup = new ButtonGroup();
        menuBarGroup = new ButtonGroup();
        for (DisplayModeOption displayMode : displayModes) {
            toolBar.add(displayMode.getRadioButton());
            toolBarGroup.add(displayMode.getRadioButton());

            displayModesMenu.add(displayMode.getMenuItem());
            menuBarGroup.add(displayMode.getMenuItem());
        }
        createRotationMode(onAngleChange);
    }

    private void createRotationMode(IntConsumer onAngleChange) {
        JButton rotationButton = new JButton();
        rotationButton.setPreferredSize(new Dimension(32, 32));
        rotationButton.setIcon(
                ImageUtils.getScaledImageFromResources("/icons/rotate_icon.png", 32, 32)
        );
        JMenuItem rotationItem = new JMenuItem("Rotation");
        rotationButton.setToolTipText("Rotation");

        FiltershopParameterDialog rotationDialog = new FiltershopParameterDialog();
        rotationDialog.addParameter(Parameters.builder()
                        .name("angle").min(-180).max(180).initial(0)
                .build());
        rotationDialog.setOnApply(() -> onAngleChange.accept(
                (int) rotationDialog.getParameterValue("angle")
        ));
        rotationButton.addActionListener(e -> rotationDialog.setVisible(true));
        rotationItem.addActionListener(e -> rotationDialog.setVisible(true));

        toolBar.add(rotationButton);
        displayModesMenu.add(rotationItem);
    }

    private void createFullSizeMode(Consumer<? super DisplayMode> onSelect) {
        DisplayModeOption fullSizeOption = new DisplayModeOption("Full size (1:1)",
                DisplayMode.FULL_SIZE, onSelect);
        displayModes.add(fullSizeOption);
        fullSizeOption.getRadioButton().setSelected(true);
        fullSizeOption.getMenuItem().setSelected(true);
        fullSizeOption.setIcons("/icons/one_to_one_mode_icon.png",
                "/icons/one_to_one_mode_selected_icon.png"
        );
    }

    private void createFitToScreenMode(Consumer<? super DisplayMode> onSelect) {
        DisplayModeOption fitToScreenOption = new DisplayModeOption("Fit to screen size",
                DisplayMode.FIT_TO_SCREEN_SIZE, onSelect
        );
        displayModes.add(fitToScreenOption);
        fitToScreenOption.setIcons("/icons/fit_to_screen_size_mode_icon.png",
                "/icons/fit_to_screen_size_mode_selected_icon.png"
        );
    }

}
