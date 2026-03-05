package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PrintFilesTest {

    private PrintFiles test;

    @BeforeEach
    void setUp() {
        test = new PrintFiles();
    }

    @Test
    void testWithNullInput() {
        Collection<File> input = null;

        assertThrows(IllegalArgumentException.class, () -> test.process(input),
                "PrintFiles will throw IllegalArgumentException whenever its argument is a null collection");
    }



}