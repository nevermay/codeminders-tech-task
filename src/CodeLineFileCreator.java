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
        return buildWithIndent(rootPath, root, EMPTY);
    }

    private static CodeLineFile buildWithIndent(final Path rootPath, final CodeLineFile root, final String indent)
            throws IOException {
        final String tab = indent + EMPTY_SPACE;
        for (final Path fileEntry : Files.list(rootPath).collect(Collectors.toList())) {
            if (Files.isDirectory(fileEntry)) {
                final String tabulatedName = tab + fileEntry.toFile().getName();
                final CodeLineFile childDirectory = new CodeLineFile(tabulatedName);
                root.addChild(childDirectory);

                buildWithIndent(fileEntry, childDirectory, tab);
            } else {
                final List<String> lines = Files.readAllLines(fileEntry, StandardCharsets.UTF_8);
                final int linesOfCode = CodeLineCounter.count(lines);

                final String tabulatedName = tab + fileEntry.toFile().getName();
                final CodeLineFile childFile = new CodeLineFile(tabulatedName, linesOfCode);

                root.addChild(childFile);
            }
        }

        return root;
    }
}
