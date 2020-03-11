import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class CodeLineCounterTest {

    private static Path ordinaryFile;
    private static Path emptyFile;
    private static Path commentsOnlyFile;

    @BeforeAll
    public static void setUp() {
        ordinaryFile = Paths.get("src/test/resources/test_files/root/dir/Test.java");
        emptyFile = Paths.get("src/test/resources/test_files/root/dir/Empty.java");
        commentsOnlyFile = Paths.get("src/test/resources/test_files/root/dir/CommentsOnly.java");
    }

    @Test
    public void testCount_GivenSingleSourceFile_ShouldCountFiveLinesOfCode() throws IOException {
        final List<String> lines = Files.readAllLines(ordinaryFile);
        final int numOfLines = CodeLineCounter.count(lines);

        assertThat(numOfLines)
                .as("Should be 5 lines of code")
                .isEqualTo(5);
    }

    @Test
    public void testCount_GivenEmptyFile_ShouldReturnZeroLinesOfCode() throws IOException {
        final List<String> lines = Files.readAllLines(emptyFile);
        final int numOfLines = CodeLineCounter.count(lines);

        assertThat(numOfLines)
                .as("There should be no code at all")
                .isEqualTo(0);
    }

    @Test
    public void testCount_GivenFileWithOnlyComments_ShouldReturnZeroLinesOfCode() throws IOException {
        final List<String> lines = Files.readAllLines(commentsOnlyFile);
        final int numOfLines = CodeLineCounter.count(lines);

        assertThat(numOfLines)
                .as("There should be no code but comments")
                .isEqualTo(0);
    }
}
