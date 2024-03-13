package ru.nsu.icg.filtershop;

import lombok.extern.log4j.Log4j2;
import ru.nsu.icg.filtershop.components.frames.FiltershopFrame;

import javax.swing.*;

@Log4j2
public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            log.error("Error: " + e.getMessage());
        }
        new FiltershopFrame();
    }

}
