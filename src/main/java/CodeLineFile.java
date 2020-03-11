import java.util.ArrayList;
import java.util.List;

public final class CodeLineFile {

    private static final String DELIMITER = ": ";

    private final String name;
    private final long numberOfCodeLines;
    private final List<CodeLineFile> children;

    public CodeLineFile(final String name, final long numberOfCodeLines) {
        this.name = name;
        this.numberOfCodeLines = numberOfCodeLines;

        children = new ArrayList<>();
    }

    public CodeLineFile(final String name) {
        this.name = name;

        numberOfCodeLines = 0;
        children = new ArrayList<>();
    }

    public void addChild(final CodeLineFile child) {
        children.add(child);
    }

    private long calculateRecursively(final List<CodeLineFile> children) {
        long qty = numberOfCodeLines;

        for (final CodeLineFile child : children) {
            if (child.getChildren() != null && !child.getChildren().isEmpty()) {
                qty += calculateRecursively(child.getChildren());
            } else {
                qty += child.calculateCodeLines();
            }
        }

        return qty;
    }

    public long calculateCodeLines() {
        return calculateRecursively(children);
    }

    public List<CodeLineFile> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public String toStringRecursively(final List<CodeLineFile> children, final StringBuilder result) {
        for (final CodeLineFile child : children) {
            if (child.getChildren() != null && !child.getChildren().isEmpty()) {
                result
                        .append(child.getName())
                        .append(DELIMITER)
                        .append(child.calculateCodeLines())
                        .append("\n");
                toStringRecursively(child.getChildren(), result);
            } else {
                result
                        .append(child.getName())
                        .append(DELIMITER)
                        .append(child.calculateCodeLines())
                        .append("\n");
            }
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return getName() + DELIMITER + calculateCodeLines() + "\n"
                + toStringRecursively(getChildren(), new StringBuilder());
    }
}
