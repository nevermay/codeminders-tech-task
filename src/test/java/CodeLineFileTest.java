import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;

public class CodeLineFileTest {

    private static Path file;
    private static Path directory;
    private static Path mixedDirectory;

    @BeforeClass
    public static void setUp() {
        file = Paths.get("src/test/resources/test_files/root/dir/Test.java");
        directory = Paths.get("src/test/resources/test_files/root/another-dir/src");
        mixedDirectory = Paths.get("src/test/resources/test_files/root");
    }

    @Test
    public void testToString_GivenSingleFile_ShouldPrintResultIfOneRow() throws IOException {
        final CodeLineFile root = new CodeLineFile(file.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(file, root);

        Assert.assertThat("Should print correctly", codeLineFile.toString(), is("Test.java: 5\n"));
    }

    @Test
    public void testToString_GivenDirectoryWithFilesOnly_ShouldPrintResultCorrectly() throws IOException {
        final CodeLineFile root = new CodeLineFile(directory.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(directory, root);

        Assert.assertThat(
                "Should print correctly",
                codeLineFile.toString(),
                is("src: 131\n Main.java: 40\n CodeLineChecker.java: 40\n CodeFile.java: 51\n")
        );
    }

    @Test
    public void testToString_GivenMixedDirectory_ShouldPrintResultAccordingToHierarchy() throws IOException {
        final CodeLineFile root = new CodeLineFile(mixedDirectory.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(mixedDirectory, root);

        Assert.assertThat(
                "Should print correctly",
                codeLineFile.toString(),
                is(
                        "root: 155\n another-dir: 139\n  Test11.java: 5\n  AnotherTest11.java: 3\n  src: 131\n   " +
                                "Main.java: 40\n   CodeLineChecker.java: 40\n   CodeFile.java: 51\n dir: 16\n  " +
                                "AnotherTest.java: 3\n  Test.java: 5\n  other-dir: 8\n   Test11.java: 5\n  " +
                                " AnotherTest11.java: 3\n  CommentsOnly.java: 0\n  Empty.java: 0\n"
                )
        );
    }
}
