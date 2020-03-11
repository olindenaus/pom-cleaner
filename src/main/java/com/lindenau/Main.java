package com.lindenau;

import com.lindenau.control.DependencyCollector;
import com.lindenau.control.PomCleaner;
import com.lindenau.control.PomFactory;
import com.lindenau.control.PomLoader;
import com.lindenau.control.PropertyReader;
import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;
import com.lindenau.entity.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static PomFactory pomFactory = new PomFactory();
    private static DependencyCollector dependencyCollector = new DependencyCollector();
    private static PomCleaner pomCleaner = new PomCleaner();
    private static PropertyReader propertyReader = new PropertyReader();

    public static void main(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Specify project's directory to analyze.");
            return;
        }
        String directory = args[0];
        PomLoader pomLoader = new PomLoader(directory);
        File[] pomFiles = pomLoader.getAllPoms();
        Map<String, Pom> poms = pomFactory.mapToPoms(pomFiles);
        Map<String, Dependency> childrenDependencies = dependencyCollector.getChildrenDependencies(new ArrayList<>(poms.values()));
        Map<String, Dependency> parentDependencies = dependencyCollector.getParentDependencies(new ArrayList<>(poms.values()));
        List<Dependency> sortedDependencies = parentDependencies.values().stream()
                .filter(dep -> childrenDependencies.containsKey(dep.getArtifactId()))
                .sorted()
                .collect(Collectors.toList());

        Pom parentPom = poms.values().stream()
                .filter(pom -> !pom.isHasParent())
                .findFirst().get();
        List<Property> properties = propertyReader.readAllProperties(parentPom);
        String cleanedPom = pomCleaner.cleanPom(parentPom, sortedDependencies, properties);

        System.out.println("Finished cleaning pom files");
    }
}
