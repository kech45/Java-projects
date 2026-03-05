package bg.sofia.uni.fmi.mjt.pipeline;

import bg.sofia.uni.fmi.mjt.pipeline.stage.Stage;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

import java.util.ArrayList;
import java.util.List;

public final class Pipeline<I, O> {

    private final Cache cache;
    private final List<Stage<?,?>> stages;

    public static <I, O> Pipeline<I, O> start(Stage<I, O> initalStage) {

        if (initalStage == null) {
            throw new IllegalArgumentException("Initial stage cannot be null!");
        }
        return new Pipeline<>(new ArrayList<>(List.of(initalStage)));

    }

    private Pipeline (List<Stage<?,?>> stages ) {
        this.stages = stages;
        this.cache = new Cache();
    }

    Pipeline (List<Stage<?,?>> stages, Cache cache) {
        this.stages = stages;
        this.cache = cache;
    }

    public <NEW_O> Pipeline<I, NEW_O> addStage(Stage<? super O, NEW_O> stage) {

        if (stage == null) {
            throw new IllegalArgumentException("Initial stage cannot be null!");
        }

        this.stages.add(stage);
        cache.clear();
        return (Pipeline<I, NEW_O>)this;

    }

    public O execute(I input) {

        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }

        Object current = input;
        if(cache.containsKey(input)) {
            return (O) cache.getCachedValue(input);
        }

        for (Stage<?, ?> stage : stages) {
            Stage<Object, Object> typedStage = (Stage<Object, Object>) stage;
            current = typedStage.execute(current);
        }

        cache.cacheValue(input, current);
        return (O) current;

    }
}
