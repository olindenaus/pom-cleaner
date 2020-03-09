package com.lindenau.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PomLoader {

    private String directory;
    private File[] files;

    public PomLoader(String startDirectory) {
        this.directory = startDirectory;
    }

    public File[] getAllPoms() {
        String projectAbsolutePath = new File(directory).getAbsolutePath();
        List<String> results = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(projectAbsolutePath))) {
            results = walk.map(Path::toString)
                    .filter(f -> f.endsWith("pom.xml"))
                    .collect(Collectors.toList());
            results.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results.stream().map(File::new).toArray(File[]::new);
    }
}
