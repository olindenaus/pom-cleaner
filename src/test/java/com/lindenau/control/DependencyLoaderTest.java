package com.lindenau.control;

import com.lindenau.entity.Pom;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DependencyLoaderTest {

    @Test
    public void shouldLoadPomDependencies() {
        Map<String, String> dependencies = new HashMap<>();
        DependencyLoader dependencyLoader = new DependencyLoader();
        assertEquals(2, dependencyLoader.loadPomDependencies(createPom()).size());
    }

    private Pom createPom() {
        return Pom.builder()
                .withArtifactId("aId")
                .withGroupId("gId")
                .withFilePath("pom.xml")
                .withLines(Collections.emptyList())
                .build();
    }

}