import java.util.List;

public class CodeLineChecker {

    public static int getLinesOfCode(List<String> lines) {
        int linesOfCode = 0;
        boolean isTrailingComment = false;
        for (final String line : lines) {
            final String trimmedLine = line.trim();

            if (isTrailingComment) {
                if (isTrailingCommentEnded(trimmedLine)) {
                    isTrailingComment = false;
                }
            } else if (isTrailingCommentStart(trimmedLine)) {
                if (!isTrailingCommentOneLiner(trimmedLine)) {
                    isTrailingComment = true;
                }
            } else if (isLineOfCode(trimmedLine)) {
                linesOfCode++;
            }
        }
        return linesOfCode;
    }

    private static boolean isTrailingCommentOneLiner(final String line) {
        return line.contains("*/") && line.contains("/*");
    }

    private static boolean isTrailingCommentEnded(final String line) {
        return line.contains("*/");
    }

    private static boolean isTrailingCommentStart(final String line) {
        return line.startsWith("/*");
    }

    private static boolean isLineOfCode(final String line) {
        return isNotComment(line) && isNotBlankLine(line);
    }

    private static boolean isNotComment(final String line) {
        return !line.startsWith("//");
    }

    private static boolean isNotBlankLine(final String line) {
        return !(line.equals("") || line.equals("\n"));
    }

}
