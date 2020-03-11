import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class CodeLineFileCreatorTest {

    private static Path file;
    private static Path directory;

    @BeforeAll
    public static void setUp() {
        file = Paths.get("src/test/resources/test_files/root/dir/Test.java");
        directory = Paths.get("src/test/resources/test_files/root/another-dir/src");
    }


    @Test
    public void testBuild_GivenSingleFile_ShouldCreateSingleCodeLineFile() throws IOException {
        final CodeLineFile root = new CodeLineFile(file.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(file, root);

        assertThat(codeLineFile.getChildren().isEmpty())
                .as("File should not have children")
                .isEqualTo(true);
    }

    @Test
    public void testBuild_GivenDirectoryWithFiles_ShouldCreateCodeFileWithCoupleOfChildren() throws IOException {
        final CodeLineFile root = new CodeLineFile(directory.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(directory, root);

        assertThat(codeLineFile.getChildren().size())
                .as("File should have children files only")
                .isEqualTo(3);
    }
}
