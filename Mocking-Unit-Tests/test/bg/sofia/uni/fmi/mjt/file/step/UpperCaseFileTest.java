package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.file.exception.EmptyFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpperCaseFileTest {

    private UpperCaseFile test;

    @BeforeEach
    void setUp() {
        test = new UpperCaseFile();
    }

    @Test
    void testProcessWithNullFile() {
        File file = null;
        assertThrows(IllegalArgumentException.class, () -> test.process(file),
                "Null files will throw IllegalArgumentException");
    }

    @Test
    void testWithValidFile() {
        File input = new File("this Is Cool");
        assertEquals("THIS IS COOL", test.process(input).getContent(), "Two to-uppercased strings must be equal");
    }
}