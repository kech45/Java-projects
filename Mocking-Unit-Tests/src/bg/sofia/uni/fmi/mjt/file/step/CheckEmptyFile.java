package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.file.exception.EmptyFileException;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

public class CheckEmptyFile implements Step<File, File> {

    @Override
    public File process(File input) {

        if(input == null || input.getContent().isEmpty()) {
            throw new EmptyFileException("File cannot be null or empty!");
        }

        return input;
    }

}
