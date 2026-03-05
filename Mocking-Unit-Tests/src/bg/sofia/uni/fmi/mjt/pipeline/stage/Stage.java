package bg.sofia.uni.fmi.mjt.pipeline.stage;

import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

import java.util.ArrayList;
import java.util.List;

public final class Stage<I, O> {

    private final List<Step<?, ?>> steps;

    public static <I, O> Stage<I, O> start(Step<I, O> initialStep) {

        if(initialStep == null) {
            throw new IllegalArgumentException("Initial step cannot be null!");
        }

        return new Stage<>(new ArrayList<>(List.of(initialStep)));
    }

    private Stage(List<Step<?, ?> >steps) {
        this.steps = new ArrayList<>(steps);
    }


    public <NEW_O> Stage<I, NEW_O> addStep(Step<? super O, NEW_O> step) {

        if(step == null) {
            throw new IllegalArgumentException("Initial step cannot be null!");
        }

        this.steps.add(step);
        return (Stage<I, NEW_O>)this;

    }

    public O execute(I input) {

        if(input == null) {
            throw new IllegalArgumentException("Input cannot be null!");
        }

        Object current = input;

        for (Step<?, ?> step : steps) {
            Step<Object, Object> typedStep = (Step<Object, Object>) step;
            current = typedStep.process(current);
        }

        return (O) current;

    }

}
