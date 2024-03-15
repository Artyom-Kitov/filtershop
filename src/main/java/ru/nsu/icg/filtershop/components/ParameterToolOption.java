package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.filtershop.components.frames.FiltershopParameterDialog;
import ru.nsu.icg.filtershop.model.tools.Tool;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ParameterToolOption {

    private final FiltershopParameterDialog dialog = new FiltershopParameterDialog();

    @Getter
    private final ToolOption toolOption;

    @Setter
    private Supplier<? extends Tool> toolSupplier;

    public ParameterToolOption(String toolName,
                               Consumer<? super Tool> onToolSelect,
                               Runnable onCancel,
                               List<Parameters> parameters) {
        parameters.forEach(p -> dialog.addParameter(p));
        toolOption = new ToolOption(toolName, null, tool -> dialog.setVisible(true), onCancel);
        dialog.setOnApply(() -> {
            toolOption.setTool(toolSupplier.get());
            onToolSelect.accept(toolOption.getTool());
        });
    }

    public float getParameter(String parameter) {
        return dialog.getParameterValue(parameter);
    }

}
