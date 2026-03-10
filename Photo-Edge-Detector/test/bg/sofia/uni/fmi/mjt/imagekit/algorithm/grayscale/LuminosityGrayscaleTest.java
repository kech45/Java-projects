package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class LuminosityGrayscaleTest {
    @Test
    void testGrayscale() {
        BufferedImage image = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0,0, (100 << 16) | (150 << 8) | 200);

        BufferedImage result = new LuminosityGrayscale().process(image);

        int expectedGray = (int) round(0.21 * 100 + 0.72 * 150 + 0.07 * 200);
        int actualGray = result.getRGB(0,0) & 0xFF;

        Assertions.assertEquals(expectedGray, actualGray, "The expectation and the result must be equal");
    }
}
