package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.GrayscaleAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale.LuminosityGrayscale;

import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {

    private final ImageAlgorithm grayScaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayScaleAlgorithm) {
        this.grayScaleAlgorithm = grayScaleAlgorithm;
    }
    private static final int[][] X_KERNEL = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    private static final int[][] Y_KERNEL = {
            {-1, -2, -1},
            {0,  0,  0},
            {1,  2,  1}

    };

    private int[][] neighbouringMatrix(BufferedImage image, int x, int y) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] result = new int[3][3];

        for(int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int newX = x + i - 1;
                int newY = y + j - 1;

                if (newX < 0 || newY < 0 || newX >= width || newY >= height) {
                    result[i][j] = 0;
                } else {
                    //Since greyScale bytes all have the same value take the last Byte of the integer!
                    result[i][j] = image.getRGB(newX,newY) & 0xFF;
                }
            }
        }
        return result;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage grayscaleImage = grayScaleAlgorithm.process(image);

        int width = grayscaleImage.getWidth();
        int height = grayscaleImage.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                int[][]neighbouringMatrix = neighbouringMatrix(grayscaleImage, i, j);

                int gx = 0;
                int gy = 0;

                for (int k =0; k < 3; k++) {
                    for(int l=0; l < 3; l++) {
                        gx += neighbouringMatrix[k][l] * X_KERNEL[k][l];
                        gy += neighbouringMatrix[k][l] * Y_KERNEL[k][l];
                    }
                }

                double magnitude = (Math.sqrt((gx*gx) + (gy*gy)));
                int pixelValue = Math.min(255, (int) Math.round(magnitude));

                int RGB = (pixelValue << 16) | (pixelValue << 8) | pixelValue;
                result.setRGB(i,j, RGB);
            }
        }
        return result;
    }
}
