package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import ru.nsu.icg.filtershop.components.frames.FiltershopParameterDialog;
import ru.nsu.icg.filtershop.model.tools.BlackWhiteTool;
import ru.nsu.icg.filtershop.model.tools.GammaTool;
import ru.nsu.icg.filtershop.model.tools.InversionTool;
import ru.nsu.icg.filtershop.model.tools.Tool;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FiltersList {

    private final List<ToolOption> tools;

    @Getter
    private final FiltershopToolBar toolBar;

    @Getter
    private final JMenu toolsMenu;

    private final ButtonGroup toolBarGroup;
    private final ButtonGroup menuBarGroup;

    public FiltersList(Consumer<? super Tool> onToolSelect, Runnable onCancel) {
        toolBar = new FiltershopToolBar();
        toolsMenu = new JMenu("Filter");

        tools = new ArrayList<>();

        ToolOption blackWhiteOption = new ToolOption("Black white",
                new BlackWhiteTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(blackWhiteOption);

        ToolOption inversionOption = new ToolOption("Inversion",
                new InversionTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(inversionOption);

        // add method
        FiltershopParameterDialog gammaDialog = new FiltershopParameterDialog();
        gammaDialog.addParameter("gamma red", 0.1f, 10f, 1f, "")
                .addParameter("gamma green", 0.1f, 10f, 1f, "")
                .addParameter("gamma blue", 0.1f, 10f, 1f, "");
        ToolOption gammaOption = new ToolOption("Gamma correction",
                new GammaTool(1, 1, 1), tool -> gammaDialog.setVisible(true),
                () -> cancelSelection(onCancel));
        gammaDialog.setOnApply(() -> {
            gammaOption.setTool(new GammaTool(
                    gammaDialog.getParameterValue("gamma red"),
                    gammaDialog.getParameterValue("gamma green"),
                    gammaDialog.getParameterValue("gamma blue")
            ));
            onToolSelect.accept(gammaOption.getTool());
        });
        tools.add(gammaOption);

        toolBarGroup = new ButtonGroup();
        menuBarGroup = new ButtonGroup();
        for (ToolOption tool : tools) {
            toolBar.add(tool.getRadioButton());
            toolBarGroup.add(tool.getRadioButton());

            toolsMenu.add(tool.getMenuItem());
            menuBarGroup.add(tool.getMenuItem());
        }
    }

    private void cancelSelection(Runnable onCancel) {
        toolBarGroup.clearSelection();
        menuBarGroup.clearSelection();
        onCancel.run();
    }

}
