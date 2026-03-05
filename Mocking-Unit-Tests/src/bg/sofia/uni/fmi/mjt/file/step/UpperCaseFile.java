package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

public class UpperCaseFile implements Step<File, File> {

    @Override
    public File process(File input) {

        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }

        input.setContent(input.getContent().toUpperCase());
        return input;

    }

}
