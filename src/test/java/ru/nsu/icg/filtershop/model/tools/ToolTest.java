package ru.nsu.icg.filtershop.model.tools;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;

class ToolTest {

    private BufferedImage testImage;

    ToolTest() {
        testImage = new BufferedImage(1 << 12, 1 << 12, BufferedImage.TYPE_INT_RGB);
        int color = 0;
        for (int y = 0; y < testImage.getHeight(); y++) {
            for (int x = 0; x < testImage.getWidth(); x++) {
                testImage.setRGB(x, y, color);
                color++;
            }
        }
    }

    @Test
    void givenImageWithAllColors_whenInversionToolApplied_thenInverted() {
        // given
        BufferedImage image = testImage;

        // when
        BufferedImage result = new BufferedImage(1 << 12, 1 << 12, BufferedImage.TYPE_INT_RGB);
        new InversionTool().applyTo(image, result);

        // then
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int c = image.getRGB(x, y);
                int cInv = result.getRGB(x, y) & 0x00ffffff;
                assertEquals((~c) & 0x00ffffff, cInv);
            }
        }
    }

}