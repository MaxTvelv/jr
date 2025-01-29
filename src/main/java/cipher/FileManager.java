package cipher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public String readFile(String filePath) {

        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))){
            while (reader.ready()) {
                stringBuilder.append(reader.readLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringBuilder.toString();
    }
    public void writeFile(String content, String filePath) {

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))){
            int bufferSize = 1024;
            int contentLength = content.length();

            for (int i = 0; i < contentLength; i += bufferSize) {
                int endOfBufferString = Math.min(i + bufferSize, contentLength);
                String bufferString = content.substring(i, endOfBufferString);

                writer.write(bufferString);
            }

            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
