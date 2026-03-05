package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.file.exception.EmptyFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SplitFileTest {

    private SplitFile test;

    @BeforeEach
    void setUp() {
        test = new SplitFile();
    }

    @Test
    void testProcessWithNullFile() {
        File file = null;
        assertThrows(IllegalArgumentException.class, () -> test.process(file),
                "Null files will throw IllegalArgumentException");
    }

    @Test
    void testProcessWithValidFiles() {
        File input = new File("Some not split String");

        assertEquals(Set.of(new File("Some"), new File("not"), new File("split"), new File("String")), test.process(input));
    }


}