package ru.nsu.icg.filtershop.components;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.icg.filtershop.components.frames.FiltershopParameterDialog;
import ru.nsu.icg.filtershop.model.tools.Tool;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ParameterToolOption {

    private final FiltershopParameterDialog dialog;

    @Getter
    private final ToolOption toolOption;

    @Setter
    private Supplier<? extends Tool> toolSupplier;

    @Setter
    private Consumer<? super Tool> onToolSelect;

    public ParameterToolOption(String toolName,
                               Runnable onToolCancel,
                               List<Parameters> parameters) {
        dialog = new FiltershopParameterDialog();

        parameters.forEach(dialog::addParameter);
        toolOption = new ToolOption(toolName, null, onToolCancel);
        toolOption.setOnSelect(tool -> dialog.setVisible(true));
        dialog.setOnApply(() -> {
            toolOption.setTool(toolSupplier.get());
            onToolSelect.accept(toolOption.getTool());
        });
    }

    public float getParameter(String parameter) {
        return dialog.getParameterValue(parameter);
    }

}
