import java.util.List;

public final class CodeLineCounter {

    private CodeLineCounter() {
        // util
    }

    public static int count(final List<String> codeLines) {
        int linesOfCode = 0;
        boolean isTrailingComment = false;

        for (final String codeLine : codeLines) {
            final String trimmedCodeLine = codeLine.trim();

            if (isTrailingComment) {
                if (isTrailingCommentEnded(trimmedCodeLine)) {
                    isTrailingComment = false;
                }
            } else if (isTrailingCommentStart(trimmedCodeLine)) {
                if (!isTrailingCommentOneLiner(trimmedCodeLine)) {
                    isTrailingComment = true;
                }
            } else if (isLineOfCode(trimmedCodeLine)) {
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
