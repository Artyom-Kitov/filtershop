package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.RGBMatrix;

@FunctionalInterface
public interface Tool {
    void applyTo(RGBMatrix matrix);
}
