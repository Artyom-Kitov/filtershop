package ru.nsu.icg.filtershop.components;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/*
Author: Mikhail Sartakov
Date: 05.03.2024
 */
public class FileManager extends JFileChooser {
    private static FileManager instance;
    private static final String IMAGE_FILES_DESCRIPTION = "Image files:  \".BMP\", \".GIF\", \".JPEG\", \".JPG\", \".PNG\"";
    private static final String IMPORT_IMAGE_DIALOG_TITLE = "Import an image";
    private static final String EXPORT_IMAGE_DIALOG_TITLE = "Export an image";
    private File lastOpenedFile = null;
    private File lastSavedFile = null;
    private final FileNameExtensionFilter imageFilter;

    private FileManager() {
        imageFilter = new FileNameExtensionFilter(IMAGE_FILES_DESCRIPTION, "jpg", "jpeg", "png", "gif", "bmp");
        setFileFilter(imageFilter);
        setMultiSelectionEnabled(false);
        setDragEnabled(true); // doesn't work for me for some reason (Mikhail Sartakov)
    }

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }

        return instance;
    }

    public BufferedImage importImage() {
        if (lastOpenedFile == null) {
            setCurrentDirectory(lastSavedFile);
        }
        else {
            setCurrentDirectory(lastOpenedFile);
        }
        setDialogTitle(IMPORT_IMAGE_DIALOG_TITLE);
        setDialogType(OPEN_DIALOG);

        int result = showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            lastOpenedFile = getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(lastOpenedFile);
                System.out.println(image.getType());
                if (image != null) {
                    // add logging
//                    showSuccessfulImportMessage();
                    return image;
                }
            }
            catch (IOException e) {
                // add logging
                // maybe throw some new exception
                showImportErrorMessage();
            }
        }
        return null;
    }

    public void exportImageAsPNG(BufferedImage image) {
        if (lastSavedFile == null) {
            setCurrentDirectory(lastOpenedFile);
        }
        else {
            setCurrentDirectory(lastSavedFile);
        }
        setDialogTitle(EXPORT_IMAGE_DIALOG_TITLE);
        setDialogType(SAVE_DIALOG);

        int result = showSaveDialog(null);
        if (result == APPROVE_OPTION) {
            lastSavedFile = getSelectedFile();
            String filePath = lastSavedFile.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
                lastSavedFile = new File(filePath);
            }

            try {
                ImageIO.write(image, "png", lastSavedFile);
                // add logging
                showSuccessfulExportMessage();
            } catch (IOException e) {
                // add logging
                // maybe throw some new exception
                showExportErrorMessage();
            }
        }
    }

    private void showSuccessfulExportMessage() {
        JOptionPane.showMessageDialog(this,
                "The image was saved successfully.",
                EXPORT_IMAGE_DIALOG_TITLE,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showExportErrorMessage() {
        JOptionPane.showMessageDialog(this,
                "Couldn't save an image. Please, try again.",
                EXPORT_IMAGE_DIALOG_TITLE,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showSuccessfulImportMessage() {
        JOptionPane.showMessageDialog(this,
                "The image was imported successfully.",
                IMPORT_IMAGE_DIALOG_TITLE,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showImportErrorMessage() {
        JOptionPane.showMessageDialog(this,
                "Couldn't open an image. Please, try again.",
                IMPORT_IMAGE_DIALOG_TITLE,
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}
