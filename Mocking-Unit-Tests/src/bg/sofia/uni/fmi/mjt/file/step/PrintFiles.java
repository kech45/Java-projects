package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

import java.util.Collection;

public class PrintFiles implements Step<Collection<File>, Collection<File>> {

    @Override
    public Collection<File> process(Collection<File> input) {

        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }

        for (File f : input) {
            System.out.println(f.getContent());
        }

        return input;
    }

}
