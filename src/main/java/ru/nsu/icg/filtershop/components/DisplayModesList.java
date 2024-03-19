package ru.nsu.icg.filtershop.components;

import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DisplayModesList {
    @Getter
    private final FiltershopToolBar toolBar;

    @Getter
    private final JMenu displayModesMenu;

    private final List<DisplayModeOption> displayModes;
    private final ButtonGroup toolBarGroup;
    private final ButtonGroup menuBarGroup;

    public DisplayModesList(Consumer<? super DisplayMode> onSelect,
                            Runnable onCancel,
                            FiltershopToolBar mainToolBar) {
        toolBar = mainToolBar;
        displayModesMenu = new JMenu("Display modes");
        displayModes = new ArrayList<>();

        createFullSizeMode(onSelect, onCancel);
        createFitToScreenMode(onSelect, onCancel);

        toolBarGroup = new ButtonGroup();
        menuBarGroup = new ButtonGroup();
        for (DisplayModeOption displayMode : displayModes) {
            toolBar.add(displayMode.getRadioButton());
            toolBarGroup.add(displayMode.getRadioButton());

            displayModesMenu.add(displayMode.getMenuItem());
            menuBarGroup.add(displayMode.getMenuItem());
        }
    }

    private void createFullSizeMode(Consumer<? super DisplayMode> onSelect, Runnable onCancel) {
        DisplayModeOption fullSizeOption = new DisplayModeOption("Full size (1:1)",
                onSelect,
                () -> cancelSelection(onCancel)
        );
        displayModes.add(fullSizeOption);
        fullSizeOption.setIcons("/icons/",
                "/icons/"
        );
    }

    private void createFitToScreenMode(Consumer<? super DisplayMode> onSelect, Runnable onCancel) {
        DisplayModeOption fitToScreenOption = new DisplayModeOption("Fit to screen size",
                onSelect,
                () -> cancelSelection(onCancel)
        );
        displayModes.add(fitToScreenOption);
        fitToScreenOption.setIcons("/icons/",
                "/icons/"
        );
    }

    private void cancelSelection(Runnable onCancel) {
        toolBarGroup.clearSelection();
        menuBarGroup.clearSelection();
        onCancel.run();
    }
}
