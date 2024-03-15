package ru.nsu.icg.filtershop.components;

import lombok.Builder;

@Builder
public record Parameters(String name, float min, float max, float initial, String warning, Float step) {
}
