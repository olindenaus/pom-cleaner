package com.lindenau.control;

import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;
import com.lindenau.xml.XmlReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DependencyLoader {

    private Map<String, Dependency> dependencies = new HashMap<>();
    private List<String> NON_SPECIAL = Arrays.asList("<groupId>", "<artifactId>", "<version>");
    private boolean started = false;
    private XmlReader xmlReader = new XmlReader();

    public Map<String, Dependency> loadPomDependencies(Pom pom) {
        List<String> lines = pom.getLines();
        List<String> dependencyLines = new ArrayList<>();
        for (String line : lines) {
            String entry = line.trim();
            if (entry.startsWith("<dependency>")) {
                dependencyLines = new ArrayList<>();
                started = true;
                continue;
            } else if (entry.startsWith("</dependency>")) {
                Dependency dependency = buildDependency(dependencyLines);
                dependencies.put(dependency.getArtifactId(), dependency);
                started = false;
                continue;
            }
            if (started) {
                dependencyLines.add(entry);
            }
        }
        return dependencies;
    }

    private Dependency buildDependency(List<String> dependencyContent) {
        Dependency.DependencyBuilder builder = Dependency.builder();
        return builder.withArtifactId(xmlReader.getAttribute(dependencyContent, "artifactId"))
                .withGroupId(xmlReader.getAttribute(dependencyContent, "groupId"))
                .withVersion(xmlReader.getOptionalAttribute(dependencyContent, "version"))
                .withSpecial(getSpecial(dependencyContent)).build();
    }

    private String getSpecial(List<String> dependencyContent) {
        return dependencyContent.stream()
                .filter(x -> NON_SPECIAL.stream().noneMatch(x::contains))
                .collect(Collectors.joining("\n"));
    }
}
