package kmitl.cs.mvc.model;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {
    //read from CSV
    public static List<String> readAll(String path) throws IOException {
        Path p = Paths.get(path);
        if (!Files.exists(p)) return new ArrayList<>();
        return Files.readAllLines(p);
    }

    public static void writeAll(String path, List<String> lines) throws IOException {
        Path p = Paths.get(path);
        if (p.getParent() != null) Files.createDirectories(p.getParent());
        Files.write(p, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
