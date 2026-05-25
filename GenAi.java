import java.io.IOException;
import java.util.Scanner;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class GenAi{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ask me anything (type 'end' to quit):");

        for (;;) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("end")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.toLowerCase().startsWith("open ")) {
                // Extract what to open after "open "
                String toOpen = input.substring(5).trim();

                if (toOpen.isEmpty()) {
                    System.out.println("Please specify what you want to open after 'open'.");
                } else {
                    openWhatever(toOpen);
                }
            } else if (!input.isEmpty()) {
                String response = localAIResponse(input);
                System.out.println("AI: " + response);
            }
        }
        scanner.close();
    }

    private static void openWhatever(String target) {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (target.equalsIgnoreCase("settings")) {
                openSettings(os);
                return;
            }

            // Replace backslashes and normalize path separators
            target = target.replace("\\", "/").trim();

            // Try as absolute path first
            Path path = Paths.get(target);
            if (!path.isAbsolute()) {
                // Try relative to user home
                String userHome = System.getProperty("user.home");
                path = Paths.get(userHome, target);
            }

            if (Files.exists(path) && Files.isDirectory(path)) {
                openFolder(path.toAbsolutePath().toString());
                return;
            }

            // If folder not found, try to find folder recursively inside user home
            String userHome = System.getProperty("user.home");
            Path userHomePath = Paths.get(userHome);

            Path found = findFolderRecursively(userHomePath, target);
            if (found != null) {
                openFolder(found.toAbsolutePath().toString());
            } else {
                System.out.println("Folder or location '" + target + "' does not exist.");
            }
        } catch (Exception e) {
            System.err.println("Error opening '" + target + "': " + e.getMessage());
        }
    }

    private static Path findFolderRecursively(Path root, String folderName) throws IOException {
        final Path[] foundPath = {null};

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (dir.getFileName().toString().equalsIgnoreCase(folderName)) {
                    foundPath[0] = dir;
                    return FileVisitResult.TERMINATE; // Stop walking once found
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                // Skip folders/files that can't be accessed
                return FileVisitResult.SKIP_SUBTREE;
            }
        });

        return foundPath[0];
    }

    private static void openSettings(String os) throws IOException {
        if (os.contains("win")) {
            Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", "start", "ms-settings:"});
            System.out.println("Opened Settings.");
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec("open /System/Applications/System\\ Preferences.app");
            System.out.println("Opened System Preferences.");
        } else {
            try {
                Runtime.getRuntime().exec("gnome-control-center");
                System.out.println("Opened Settings.");
            } catch (IOException e) {
                try {
                    Runtime.getRuntime().exec("systemsettings5");
                    System.out.println("Opened Settings.");
                } catch (IOException ex) {
                    System.out.println("Settings app not found on your system.");
                }
            }
        }
    }

   private static void openFolder(String folderPath) {
    try {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            Runtime.getRuntime().exec(
                new String[]{"explorer.exe", folderPath}
            );
        } else if (os.contains("mac")) {
            Runtime.getRuntime().exec(
                new String[]{"open", folderPath}
            );
        } else {
            Runtime.getRuntime().exec(
                new String[]{"xdg-open", folderPath}
            );
        }

        System.out.println("Opened: " + folderPath);

    } catch (IOException e) {
        System.err.println("Failed to open folder: " + e.getMessage());
    }
}

    private static String localAIResponse(String input) {
        input = input.toLowerCase();

        if (input.contains("hello") || input.contains("hi")) {
            return "Hello! How can I assist you today?";
        } else if (input.contains("how are you")) {
            return "I'm just a program, but I'm functioning as expected!";
        } else if (input.contains("time")) {
            return "I don't have a clock, but you can check your system time!";
        } else {
            return "Sorry, I don't understand that yet. Try asking something else.";
        }
    }
}