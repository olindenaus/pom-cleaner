package com.lindenau;

import com.lindenau.control.DependencyCollector;
import com.lindenau.control.PomCleaner;
import com.lindenau.control.PomFactory;
import com.lindenau.control.PomLoader;
import com.lindenau.control.PomSaver;
import com.lindenau.control.PropertyReader;
import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;
import com.lindenau.entity.Property;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

        List<Dependency> sortedDependencies = sortAndFilterParentDependencies(poms);
        Pom parentPom = getParent(poms);

        List<Property> properties = propertyReader.readAllProperties(parentPom);
        String cleanedPom = pomCleaner.cleanPom(parentPom, sortedDependencies, properties);
        PomSaver pomSaver = new PomSaver();
        pomSaver.overWritePom(parentPom, cleanedPom);
        System.out.println("Finished cleaning pom files");
    }

    private static List<Dependency> sortAndFilterParentDependencies(Map<String, Pom> poms) {
        Map<String, Dependency> childrenDependencies = dependencyCollector.getChildrenDependencies(new ArrayList<>(poms.values()));
        Map<String, Dependency> parentDependencies = dependencyCollector.getParentDependencies(new ArrayList<>(poms.values()));
        return parentDependencies.values().stream()
                .filter(dep -> childrenDependencies.containsKey(dep.getArtifactId()))
                .sorted()
                .collect(Collectors.toList());
    }

    private static Pom getParent(Map<String, Pom> poms) {
        return poms.values().stream()
                .filter(pom -> !pom.isHasParent())
                .findFirst().get();
    }
}
