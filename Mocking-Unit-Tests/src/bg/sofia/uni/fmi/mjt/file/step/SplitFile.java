package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

import java.util.HashSet;
import java.util.Set;

public class SplitFile implements Step<File, Set<File>> {

    @Override
    public Set<File> process(File input) {

        if(input == null) {
            throw new IllegalArgumentException("Input file cannot be null!");
        }

        Set<File> result = new HashSet<>();
        String[] parts = input.getContent().split("\\s");

        for (String part : parts) {
            result.add(new File(part));
        }

        return result;

    }

}
