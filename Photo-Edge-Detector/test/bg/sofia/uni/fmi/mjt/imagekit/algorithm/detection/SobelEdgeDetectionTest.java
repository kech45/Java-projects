package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;

class SobelEdgeDetectionTest {
    @Test
    void testEdgeDetectionNoEdges() {
        BufferedImage image = new BufferedImage(5,5,BufferedImage.TYPE_INT_RGB);

        //full-white image, no edges
        for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                image.setRGB(i,j,0xFFFFF);
            }
        }

        BufferedImage result = new SobelEdgeDetection(new LuminosityGrayscale()).process(image);
        int centerPixel = result.getRGB(2,2) & 0xFF;
        Assertions.assertEquals(0, centerPixel, "Fully white image should have no edges");
    }
}