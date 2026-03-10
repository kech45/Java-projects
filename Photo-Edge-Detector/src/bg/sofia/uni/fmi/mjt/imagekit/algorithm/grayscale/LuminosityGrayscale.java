package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.image.BufferedImage;

import static java.lang.Math.round;

public class LuminosityGrayscale implements GrayscaleAlgorithm {

    @Override
    public BufferedImage process(BufferedImage image) {
        if(image == null) {
            throw new IllegalArgumentException("Image cannot be null!");
        }

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int rgb = image.getRGB(i, j);

                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int gray = (int) round(0.21 * red + 0.72 * green + 0.07 * blue);

                int grayRGB = (gray << 16) | (gray << 8) | gray;
                result.setRGB(i,j, grayRGB);
            }
        }
        return result;
    }

}
