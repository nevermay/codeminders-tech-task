import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a filename: ");
            final String filename = scanner.next();

            try {
                final Path path = Paths.get(filename);
                final CodeLineFile file = CodeLineFileCreator.build(
                        path, new CodeLineFile(path.getFileName().toString())
                );

                System.out.print(file);
            } catch (IOException e) {
                System.out.println("File not found. Please enter valid path");
            }
        }
    }
}
