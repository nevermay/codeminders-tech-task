import java.util.ArrayList;
import java.util.List;

public class CodeFile {

    private final String name;
    private final long codeLines;
    private final List<CodeFile> files;

    public CodeFile(final String name, final long codeLines) {
        this.name = name;
        this.codeLines = codeLines;

        files = new ArrayList<>();
    }

    public void addFiles(final CodeFile file) {
        files.add(file);
    }


    private long calculateRecursively(final List<CodeFile> codeFiles) {
        long count = codeLines;
        for (final CodeFile codeFile : codeFiles) {
            if (codeFile.getFiles() != null && !codeFile.getFiles().isEmpty()) {
                count += calculateRecursively(codeFile.getFiles());
            } else {
                count += codeFile.calculateCodeLines();
            }
        }

        return count;
    }

    public long calculateCodeLines() {
        return calculateRecursively(files);
    }

    public List<CodeFile> getFiles() {
        return files;
    }

    public String getName() {
        return name;
    }

    public String toStringRecursively(final List<CodeFile> codeFiles, final StringBuilder builder) {
        for (final CodeFile codeFile : codeFiles) {
            if (codeFile.getFiles() != null && !codeFile.getFiles().isEmpty()) {
                builder.append(codeFile.getName()).append(": ").append(codeFile.calculateCodeLines()).append("\n");
                toStringRecursively(codeFile.getFiles(), builder);
            } else {
                builder.append(codeFile.getName()).append(": ").append(codeFile.calculateCodeLines()).append("\n");
            }
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return getName() + ": " + calculateCodeLines() + "\n"
                + toStringRecursively(getFiles(), new StringBuilder());
    }
}
