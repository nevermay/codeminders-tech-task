import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public final class CodeLineFileCreator {

    private static final String EMPTY = "";
    private static final String EMPTY_SPACE = " ";

    public static CodeLineFile build(final Path rootPath, final CodeLineFile root)
            throws IOException {
        if (Files.isRegularFile(rootPath)) {
            return buildSingleFile(rootPath, EMPTY);
        } else {
            return buildWithIndent(rootPath, root, EMPTY);
        }
    }

    private static CodeLineFile buildWithIndent(final Path rootPath, final CodeLineFile root, final String indent)
            throws IOException {
        final String tab = indent + EMPTY_SPACE;
        for (final Path childPath : Files.list(rootPath).collect(Collectors.toList())) {
            if (Files.isDirectory(childPath)) {
                final String tabulatedName = tab + childPath.toFile().getName();
                final CodeLineFile childDirectory = new CodeLineFile(tabulatedName);
                root.addChild(childDirectory);

                buildWithIndent(childPath, childDirectory, tab);
            } else {
                final CodeLineFile childFile = buildSingleFile(childPath, tab);
                root.addChild(childFile);
            }
        }

        return root;
    }

    private static CodeLineFile buildSingleFile(final Path filePath, final String tab) throws IOException {
        final List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        final int linesOfCode = CodeLineCounter.count(lines);

        final String tabulatedName = tab + filePath.toFile().getName();
        return new CodeLineFile(tabulatedName, linesOfCode);
    }
}
