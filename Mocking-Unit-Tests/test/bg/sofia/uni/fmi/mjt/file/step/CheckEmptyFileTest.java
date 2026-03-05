package bg.sofia.uni.fmi.mjt.file.step;

import static org.junit.jupiter.api.Assertions.*;
import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.file.exception.EmptyFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckEmptyFileTest {

    private CheckEmptyFile test;

    @BeforeEach
    void setUp() {
        test = new CheckEmptyFile();
    }

    @Test
    void testProcessWithValidFile() {
        File file = new File("Some content");
        assertEquals("Some content", test.process(file).getContent(),
                "Files with the same content should be equal");
    }

    @Test
    void testProcessWithEmptyFile() {
        File file = new File("");
        assertThrows(EmptyFileException.class, () -> test.process(file), "Blank files will throw EmptyFileException");
    }

    @Test
    void testProcessWithNullFile() {
        File file = null;
        assertThrows(EmptyFileException.class, () -> test.process(file), "Null files will throw EmptyFileException");
    }
}
