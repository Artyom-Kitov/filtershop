package ru.nsu.icg.filtershop.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorUtils {
    public int getRed(int RGB) {
        return (RGB >> 16) & 0xFF;
    }

    public int getGreen(int RGB) {
        return (RGB >> 8) & 0xFF;
    }

    public int getBlue(int RGB) {
        return RGB & 0xFF;
    }
}
