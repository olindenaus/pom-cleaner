package com.lindenau.control;

import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DependencyCollector {

    private DependencyLoader dependencyLoader = new DependencyLoader();

    public Map<String, Dependency> getChildrenDependencies(List<Pom> poms) {
        Map<String, Dependency> dependencies = new HashMap<>();
        poms.stream()
                .filter(pom -> !pom.getParentArtifactId().isEmpty())
                .map(pom -> dependencyLoader.loadPomDependencies(pom))
                .forEach(dependencies::putAll);
        return dependencies;
    }

    public Map<String, Dependency> getParentDependencies(List<Pom> poms) {
        return poms.stream()
                .filter(pom -> pom.getParentArtifactId().isEmpty())
                .map(parentPom -> dependencyLoader.loadDependencyManagement(parentPom))
                .findFirst().get();
    }

}
