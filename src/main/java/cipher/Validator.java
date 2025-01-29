package cipher;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    private static final List<String> FORBIDDEN_DIRS_FILES = List.of(".bash_history", ".bash_profile", "etc", "proc");

    public void validateKey(int key, char[] alphabet) {
        if( !(key >= 0 && alphabet.length - 1 >= key)) {
            throw new RuntimeException("Invalid key");
        }
    }

    public void validateForReading(String filename) {
       Path path = validatePath(filename);;

        if (Files.notExists(path)) {
            throw new RuntimeException("File doesn't exist");
        }

        if (Files.isDirectory(path)) {
            throw new RuntimeException("File is directory");
        }

        if (!Files.isReadable(path)) {
            throw new RuntimeException("You don't have right to read from the file");
        }
    }

    public void validateForWriting(String filename) {
        Path path = validatePath(filename);

        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                throw new RuntimeException("File is directory");
            }

            if (!Files.isWritable(path)) {
                throw new RuntimeException("File " + path + " is not accessible for writing");
            }
        }
    }

    private Path validatePath(String filename) {
        for (String pathPart: filename.split(Pattern.quote(System.getProperty("file.separator")))) {
            if (FORBIDDEN_DIRS_FILES.contains(pathPart)) {
                throw new RuntimeException("Path contains forbidden part: " + pathPart);
            }
        }

        return Path.of(filename);
    }
}
