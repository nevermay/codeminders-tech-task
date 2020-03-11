import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;


public class CodeLineFileTest {

    private static Path file;
    private static Path directory;
    private static Path mixedDirectory;

    @BeforeAll
    public static void setUp() {
        file = Paths.get("src/test/resources/test_files/root/dir/Test.java");
        directory = Paths.get("src/test/resources/test_files/root/another-dir/src");
        mixedDirectory = Paths.get("src/test/resources/test_files/root");
    }

    @Test
    public void testToString_GivenSingleFile_ShouldPrintResultIfOneRow() throws IOException {
        final CodeLineFile root = new CodeLineFile(file.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(file, root);

        assertThat(codeLineFile.toString())
                .as("Should print correctly")
                .isEqualTo("Test.java: 5\n");
    }

    @Test
    public void testToString_GivenDirectoryWithFilesOnly_ShouldPrintResultCorrectly() throws IOException {
        final CodeLineFile root = new CodeLineFile(directory.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(directory, root);

        assertThat(codeLineFile.toString())
                .as("Should print correctly")
                .isEqualTo("src: 131\n Main.java: 40\n CodeLineChecker.java: 40\n CodeFile.java: 51\n");
    }

    @Test
    public void testToString_GivenMixedDirectory_ShouldPrintResultAccordingToHierarchy() throws IOException {
        final CodeLineFile root = new CodeLineFile(mixedDirectory.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(mixedDirectory, root);

        assertThat(codeLineFile.toString())
                .as("Formatting for hierarchy should persist")
                .isEqualTo(
                        "root: 155\n another-dir: 139\n  Test11.java: 5\n  AnotherTest11.java: 3\n  src: 131\n   " +
                                "Main.java: 40\n   CodeLineChecker.java: 40\n   CodeFile.java: 51\n dir: 16\n  " +
                                "AnotherTest.java: 3\n  Test.java: 5\n  other-dir: 8\n   Test11.java: 5\n  " +
                                " AnotherTest11.java: 3\n  CommentsOnly.java: 0\n  Empty.java: 0\n"

                );
    }
}
