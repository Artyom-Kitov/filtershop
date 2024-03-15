package ru.nsu.icg.filtershop.components.frames;

import lombok.AllArgsConstructor;
import ru.nsu.icg.filtershop.components.ParameterPanel;

import javax.swing.*;
import java.util.List;

/*
Author: Mikhail Sartakov
Date: 15.03.2024
*/
@AllArgsConstructor
public class FiltershopParameterDialog extends JDialog {
    private List<ParameterPanel<? extends Number>> parameters;
}
