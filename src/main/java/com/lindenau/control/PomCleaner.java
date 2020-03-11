package com.lindenau.control;

import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;
import com.lindenau.entity.Property;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PomCleaner {

    private boolean dependencySection = false;
    private boolean propertiesSection = false;
    private boolean firstPropertySection = false;
    private final List<String> PROPERTIES_TO_SAVE = Arrays.asList("maven.compiler.source", "maven.compiler.target", "maven.javadoc.skip",
            "main.basedir", "maven.test.skip");

    public String cleanPom(Pom pom, List<Dependency> dependencies, List<Property> properties) {
        StringBuilder content = new StringBuilder();
        for (String line : pom.getLines()) {
            if (!firstPropertySection) {
                replacePropertiesSection(line, content, properties, dependencies);
            }
            replaceDependencyManagement(line, content, dependencies);
            if (!dependencySection && !propertiesSection) {
                content.append(line);
            }
        }
        return content.toString();
    }

    private StringBuilder replacePropertiesSection(String line, StringBuilder sb, List<Property> properties, List<Dependency> dependencies) {
        if (line.contains("properties>")) {
            if (propertiesSection) {
                sb.append(addCleanedProperties(properties, dependencies));
                sb.append("\n");
                propertiesSection = false;
                firstPropertySection = true;
                return sb;
            }
            sb.append(line).append("\n");
            propertiesSection = true;
            return sb;
        }
        if (PROPERTIES_TO_SAVE.stream().anyMatch(prop -> line.trim().contains(prop))) {
            sb.append(line).append("\n");
        }
        return sb;
    }

    private String addCleanedProperties(List<Property> properties, List<Dependency> dependencies) {
        List<String> dependenciesVersions = dependencies.stream()
                .map(dependency -> dependency.getVersion()
                        .replace("${", "").replace("}", ""))
                .collect(Collectors.toList());
        return properties.stream()
                .filter(property -> dependenciesVersions.contains(property.getName()))
                .map(Property::toString).sorted().collect(Collectors.joining("\n"));
    }

    private StringBuilder replaceDependencyManagement(String line, StringBuilder sb, List<Dependency> dependencies) {
        if (line.contains("dependencyManagement>")) {
            if (dependencySection) {
                sb.append("<dependencies>");
                sb.append(addNewDependencySection(dependencies));
                sb.append("</dependencies>");
                sb.append("\n");
                dependencySection = false;
                return sb;
            }
            sb.append(line).append("\n");
            dependencySection = true;
            return sb;
        }
        return sb;
    }

    private String addNewDependencySection(List<Dependency> dependencies) {
        return dependencies.stream().map(Dependency::toString).collect(Collectors.joining());
    }
}
