import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a filename: ");
            final String filename = scanner.next();

            try {
                Path path = Paths.get(filename);
                CodeFile file = listFilesForFolder(path, new CodeFile(path.toString(), 0), "");

                System.out.print(file);
            } catch (IOException e) {
                e.getStackTrace();
            }
        }
    }

    public static CodeFile listFilesForFolder(final Path path, final CodeFile file, final String indent)
            throws IOException {
        final String tab = indent + " ";
        for (final Path fileEntry : Files.list(path).collect(Collectors.toList())) {
            if (Files.isDirectory(fileEntry)) {
                CodeFile file1 = new CodeFile(tab + fileEntry.toFile().getName(), 0);
                file.addFiles(file1);
                listFilesForFolder(fileEntry, file1, tab);
            } else {
                final List<String> lines = Files.readAllLines(fileEntry, StandardCharsets.UTF_8);
                int linesOfCode = CodeLineChecker.getLinesOfCode(lines);

                file.addFiles(new CodeFile(tab + fileEntry.toFile().getName(), linesOfCode));
            }
        }

        return file;
    }
}
