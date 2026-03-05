package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

import java.util.Collection;

public class CountFiles implements Step<Collection<File>, Integer> {

    @Override
    public Integer process(Collection<File> input) {

        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }

        return input.size();

    }

}
