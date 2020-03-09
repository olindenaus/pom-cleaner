package com.lindenau.control;

import com.lindenau.entity.Pom;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PomFactory {

    PomReader pomReader = new PomReader();

    public Map<String, Pom> mapToPoms(File[] files) {
        return Arrays.stream(files)
                .map(this::mapToPom)
                .collect(Collectors.toMap(Pom::getArtifactId, pom -> pom));
    }

    public Pom mapToPom(File file) {
        return pomReader.readPomFile(file);
    }
}
