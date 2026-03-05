package bg.sofia.uni.fmi.mjt.pipeline.step;

public interface Step<I, O> {
    O process(I input);
}
