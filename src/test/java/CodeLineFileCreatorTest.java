import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;

public class CodeLineFileCreatorTest {

    private static Path file;
    private static Path directory;

    @BeforeClass
    public static void setUp() {
        file = Paths.get("src/test/resources/test_files/root/dir/Test.java");
        directory = Paths.get("src/test/resources/test_files/root/another-dir/src");
    }


    @Test
    public void testBuild_GivenSingleFile_ShouldCreateSingleCodeLineFile() throws IOException {
        final CodeLineFile root = new CodeLineFile(file.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(file, root);

        Assert.assertThat("File should not have children", codeLineFile.getChildren().isEmpty(), is(true));
    }

    @Test
    public void testBuild_GivenDirectoryWithFiles_ShouldCreateCodeFileWithCoupleOfChildren() throws IOException {
        final CodeLineFile root = new CodeLineFile(directory.getFileName().toString());
        final CodeLineFile codeLineFile = CodeLineFileCreator.build(directory, root);

        Assert.assertThat("File should not have children", codeLineFile.getChildren().size(), is(3));
    }
}
