package bg.sofia.uni.fmi.mjt.imagekit.filesystem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LocalFileSystemManagerTest {
    private FileSystemImageManager manager = new LocalFileSystemImageManager();

    @Test
    void testLoadNullImage() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> manager.loadImage(null),
                "Cannot load null image");
    }

    @Test
    void testLoadNonExistentImage(@TempDir Path tempDir) {
        File nonExistent = tempDir.resolve("nonexistent.png").toFile();
        Assertions.assertThrows(IOException.class, () -> manager.loadImage(nonExistent),
                "Cannot load non existent file");
    }

    @Test
    void testLoadDirectoryNotAllowed(@TempDir Path tempDir) {
        File directory = tempDir.toFile();
        Assertions.assertThrows(IOException.class, () -> manager.loadImage(directory));
    }

    @Test
    void testLoadImageWithWrongExtension(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("sample.txt");
        Files.createFile(file);
        Assertions.assertThrows(IOException.class, () -> manager.loadImage(file.toFile()));
    }

    @Test
    void testValidLoadImage(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("img.png");
        BufferedImage image = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        ImageIO.write(image, "png", file.toFile());

        BufferedImage result = manager.loadImage(file.toFile());
        Assertions.assertEquals(image.getRGB(0,0), result.getRGB(0,0));
    }

    @Test
    void testLoadNullDirectory(@TempDir Path tempDir) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> manager.loadImagesFromDirectory(null),
                "Directory cannot be null");
    }

    @Test
    void testLoadNonExistentDirectory(@TempDir Path tempDir) {
        Path directory = tempDir.resolve("nonexistent");
        Assertions.assertThrows(IOException.class, () -> manager.loadImagesFromDirectory(directory.toFile()),
                "Cannot load nonexistent directory");
    }

    @Test
    void testLoadNonDirectory(@TempDir Path tempDir) throws Exception {
        Path file = tempDir.resolve("somefile.txt");
        Files.createFile(file);
        Assertions.assertThrows(IOException.class, () -> manager.loadImagesFromDirectory(file.toFile()),
                "Directory must be a directory");
    }

    @Test
    void testSaveImageWrongSource(@TempDir Path tempDir) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> manager.saveImage(null, tempDir.toFile()),
                "Source cannot be null");
    }

    @Test
    void testSaveImageWrongDestination(@TempDir Path tempDir) {
        BufferedImage image = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        Assertions.assertThrows(IllegalArgumentException.class, () -> manager.saveImage(image, null),
                "Destination cannot be null");
    }

    @Test
    void testSaveAlreadyExistingFile(@TempDir Path tempDir) throws Exception {
        BufferedImage image = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        Path file = tempDir.resolve("somefile.png");
        Files.createFile(file);
        Assertions.assertThrows(IOException.class, () -> manager.saveImage(image, file.toFile()),
                "File already exists");
    }

    @Test
    void testSaveNonExistentParent(@TempDir Path tempDir) throws Exception {
        BufferedImage image = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        Path nonExistentParent = tempDir.resolve("nonexistent/image.png");
        Assertions.assertThrows(IOException.class, () -> manager.saveImage(image, nonExistentParent.toFile()),
                "File parent dosen't exist");
    }

    @Test
    void testValidSaveImage(@TempDir Path tempDir) throws Exception {
        BufferedImage image = new BufferedImage(1,1, BufferedImage.TYPE_INT_RGB);
        Path file = tempDir.resolve("path.png");

        manager.saveImage(image, file.toFile());
        Assertions.assertTrue(file.toFile().exists());
    }
}
