package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import ru.nsu.icg.filtershop.components.frames.FiltershopParameterDialog;
import ru.nsu.icg.filtershop.model.tools.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

        // black white
        ToolOption blackWhiteOption = new ToolOption("Black white",
                new BlackWhiteTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(blackWhiteOption);

        // inversion
        ToolOption inversionOption = new ToolOption("Inversion",
                new InversionTool(), onToolSelect, () -> cancelSelection(onCancel));
        tools.add(inversionOption);

        // gamma
        ParameterToolOption gammaOption = new ParameterToolOption("Gamma correction", onToolSelect, onCancel,
                List.of(Parameters.builder().name("gamma red").min(0.1f).max(10).initial(1).warning("invalid red gamma value")
                        .build(),
                Parameters.builder().name("gamma green").min(0.1f).max(10).initial(1).warning("invalid green gamma value")
                        .build(),
                Parameters.builder().name("gamma blue").min(0.1f).max(10).initial(1).warning("invalid blue gamma value")
                        .build()));
        gammaOption.setToolSupplier(() -> new GammaTool(gammaOption.getParameter("gamma red"),
                gammaOption.getParameter("gamma green"),
                gammaOption.getParameter("gamma blue")));
        tools.add(gammaOption.getToolOption());

        // blur
        ParameterToolOption blurOption = new ParameterToolOption("Gaussian blur", onToolSelect,
                onCancel, List.of(
                        Parameters.builder().name("n").min(3).max(11).initial(3).step(2.0f).warning("invalid n")
                                .build(),
                        Parameters.builder().name("sigma").min(0.1f).max(100).initial(1).warning("invalid standard deviation")
                                .build()
        ));
        blurOption.setToolSupplier(() -> new BlurTool(blurOption.getParameter("sigma"),
                (int) blurOption.getParameter("n")
        ));
        tools.add(blurOption.getToolOption());

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
