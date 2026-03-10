package bg.sofia.uni.fmi.mjt.imagekit.filesystem;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class LocalFileSystemImageManager implements FileSystemImageManager {


    @Override
    public BufferedImage loadImage(File imageFile) throws IOException {
        if(imageFile == null) {
            throw new IllegalArgumentException("Image File cannot be null!");
        }

        if(!imageFile.exists() || !imageFile.isFile())
        {
            throw new IOException("Image file does not exist or isn't a file!");
        }

        String fileName = imageFile.getName().toLowerCase();
        if(!(fileName.endsWith(".png") || fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg") || fileName.endsWith(".bmp"))) {
            throw new IOException("Invalid file type!");
        }

        return ImageIO.read(imageFile);
    }

    @Override
    public List<BufferedImage> loadImagesFromDirectory(File imagesDirectory) throws IOException {
        if(imagesDirectory == null) {
            throw new IllegalArgumentException("Image directory cannot be null!");
        }

        if(!imagesDirectory.exists() || !imagesDirectory.isDirectory()) {
            throw new IOException("Image directory doesn't exist or is not a directory!");
        }

        File[] allFiles = imagesDirectory.listFiles();

        if(allFiles == null) {
            throw new IOException("Failed to list files!");
        }

        List<BufferedImage> result = new ArrayList<>(allFiles.length);

        for(File file : allFiles) {
            result.add(loadImage(file));
        }

        return result;
    }

    @Override
    public void saveImage(BufferedImage image, File imageFile) throws IOException {
        if (image == null || imageFile == null) {
            throw new IllegalArgumentException("Invalid source or destination file!");
        }

        File parent = imageFile.getParentFile();
        if((imageFile.exists()) || (parent != null && !parent.exists())) {
            throw new IOException("File already exists or parent directory doesn't exist!");
        }

        String fileName = imageFile.getName().toLowerCase();
        String format = fileName.substring(fileName.lastIndexOf(".")+1);
        ImageIO.write(image, format ,imageFile);

    }
}
