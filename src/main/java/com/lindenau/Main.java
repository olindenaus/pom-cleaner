package com.lindenau;

import com.lindenau.control.DependencyCollector;
import com.lindenau.control.PomFactory;
import com.lindenau.control.PomLoader;
import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static PomFactory pomFactory = new PomFactory();
    private static DependencyCollector dependencyCollector = new DependencyCollector();

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
        for(Dependency dep : sortedDependencies) {
            System.out.println(dep);
        }

        System.out.println("Finished cleaning pom files");
    }
}
