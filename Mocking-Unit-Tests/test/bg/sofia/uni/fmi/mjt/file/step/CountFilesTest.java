package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class CountFilesTest {

    private CountFiles test;

    @BeforeEach
    void setUp() {
        test = new CountFiles();
    }

    @Test
    void testWithCountingMultipleFiles() {

        File file1 = new File("Some file");
        File file2 = new File("Some string");
        Collection<File> input = new HashSet<>();
        input.add(file1);
        input.add(file2);

        assertEquals(2, test.process(input),
                "CountFiles should return the correct number of files in the collection");
    }

    @Test
    void testWithCountingZeroFiles() {
        Collection<File> input = new HashSet<>();

        assertEquals(0, test.process(input),
                "CountFiles should return the correct number of files in the collection");
    }

    @Test
    void testWithInputCollectionNull() {
        Collection<File> input = null;

        assertThrows(IllegalArgumentException.class, () -> test.process(input),
                "CountFiles will throw IllegalArgumentException whenever its argument is a null collection");
    }

}