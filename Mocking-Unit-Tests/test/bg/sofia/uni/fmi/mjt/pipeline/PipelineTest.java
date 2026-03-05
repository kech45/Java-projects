package bg.sofia.uni.fmi.mjt.pipeline;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PipelineTest {

    private Pipeline<File, File> test;

    @Mock
    Stage<File, File> mockStage1;

    @Mock
    Stage<File, Integer> mockStage2;

    @Test
    void testPipelineStartWithEmptyInitialStep() {
        assertThrows(IllegalArgumentException.class, () -> Pipeline.start(null),
                "Initializing stage must not be null, otherwise an IllegalArgumentException will be thrown");
    }

    @Test
    void testPipelineStartWithValidInitialStep() {
        assertDoesNotThrow(() -> Pipeline.start(mockStage1));
    }

    @Test
    void testPipelineExecuteWithNullInput() {
        test = Pipeline.start(mockStage1);
        assertThrows(IllegalArgumentException.class, () -> test.execute(null),
                "Executing a null input is not allowed, IllegalArgumentException will be thrown");
    }

    @Test
    void testPipelineExecuteWithSingleValidInput() {

        File input1 = new File("test");
        File output1 = new File("TEST");

        when(mockStage1.execute(input1)).thenReturn(output1);
        test = Pipeline.start(mockStage1);

        assertEquals(output1, test.execute(input1));

    }

    @Test
    void testPipelineExecuteWithValidCaching() {

        File input1 = new File("test");
        File output1 = new File("TEST");

        when(mockStage1.execute(input1)).thenReturn(output1);
        test = Pipeline.start(mockStage1);

        test.execute(input1);
        assertEquals(output1, test.execute(input1));

        verify(mockStage1, times(1)).execute(input1);

    }

    @Test
    void testPipelineExecuteWithMultipleValidInput() {

        File input1 = new File("test");
        File output1 = new File("TEST");
        File input2 = output1;
        int output2 = 1;

        when(mockStage1.execute(input1)).thenReturn(output1);
        when(mockStage2.execute(input2)).thenReturn(output2);

        test = Pipeline.start(mockStage1);
        test.addStage(mockStage2);

        assertEquals(output2, test.execute(input1));

    }

    @Test
    void testPipelineAddWithEmptyStage() {
        test = Pipeline.start(mockStage1);
        assertThrows(IllegalArgumentException.class, () -> test.addStage(null),
                "The stage, to be added, must not be null, otherwise an IllegalArgumentException will be thrown");
    }

    @Test
    void testPipelineAddValidStageAndClearCache() {
        Cache cache = new Cache();
        File input = new File("test");
        File output = new File("TEST");

        when(mockStage1.execute(input)).thenReturn(output);

        test = new Pipeline<>(new ArrayList<>(List.of(mockStage1)), cache);
        test.execute(input);
        test.addStage(mockStage2);

        assertTrue(cache.isEmpty());

    }

}