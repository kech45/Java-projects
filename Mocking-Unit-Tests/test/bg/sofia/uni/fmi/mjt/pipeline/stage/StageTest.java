package bg.sofia.uni.fmi.mjt.pipeline.stage;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StageTest {

    private Stage<File, File> test;

    Step<File, File> mockStep1 = mock();

    Step<File, Integer> mockStep2 = mock();

    @Test
    void testStageStartWithEmptyInitialStep() {
        assertThrows(IllegalArgumentException.class, () -> Stage.start(null),
                "Initializing step must not be null, otherwise an IllegalArgumentException will be thrown");
    }

    @Test
    void testStageStartWithValidInitialStep() {
        assertDoesNotThrow(() -> Stage.start(mockStep1));
    }

    @Test
    void testStageAddWithEmptyStep() {
        test = Stage.start(mockStep1);
        assertThrows(IllegalArgumentException.class, () -> test.addStep(null),
                "The step, to be added, must not be null, otherwise an IllegalArgumentException will be thrown");
    }

    @Test
    void testStageAddWithValidStep() {
        test = Stage.start(mockStep1);
        assertDoesNotThrow(() -> test.addStep(mockStep2));
    }

    @Test
    void testStageExecuteWithNullInput() {

        test = Stage.start(mockStep1);
        assertThrows(IllegalArgumentException.class, () -> test.execute(null),
                "Input cannot be null, otherwise IllegalArgumentException will be thrown");

    }

    @Test
    void testStageExecuteWithValidSingleStepInput() {

        File input = new File("test");
        File output = new File("TEST");

        when(mockStep1.process(input)).thenReturn(output);

        test = Stage.start(mockStep1);

        assertEquals(output, test.execute(input));

    }

    @Test
    void testStageExecuteWithValidMultipleStepInput() {

        File input1 = new File("test");
        File output1 = new File("TEST");
        File input2 = output1;
        int output2 = 1;

        when(mockStep1.process(input1)).thenReturn(output1);
        when(mockStep2.process(input2)).thenReturn(output2);

        test = Stage.start(mockStep1);
        test.addStep(mockStep2);

        assertEquals(output2, test.execute(input1));

    }


}
