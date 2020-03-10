package com.lindenau.control;

import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DependencyCollectorTest {

    private DependencyCollector dependencyCollector = new DependencyCollector();

    @Test
    public void shouldGetChildrenDependenciesMap() {
        List<Pom> poms = createPoms();
        Map<String, Dependency> dependencies = dependencyCollector.getChildrenDependencies(poms);
        Assert.assertEquals(3, dependencies.size());
    }

    private List<Pom> createPoms() {
        String lines = "<dependencies>\n" +
                "        <dependency>\n" +
                "            <groupId>org.slf4j</groupId>\n" +
                "            <artifactId>slf4j-api</artifactId>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>org.concordion</groupId>\n" +
                "            <artifactId>concordion</artifactId>\n" +
                "            <scope>test</scope>\n" +
                "        </dependency>\n"+
                "</dependencies>";
        String childrenLines = "<dependencies>\n" +
                "        <dependency>\n" +
                "            <groupId>org.slf4j</groupId>\n" +
                "            <artifactId>slf4j-api</artifactId>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>org.somethingelse</groupId>\n" +
                "            <artifactId>somethingelse</artifactId>\n" +
                "            <scope>test</scope>\n" +
                "        </dependency>\n"+
                "</dependencies>";
        List<Pom> poms = new ArrayList<>();
        String pId = "parentArtifactId";
        poms.add(createPom(lines, pId));
        poms.add(createChildrenPom(lines, "children1", pId));
        poms.add(createChildrenPom(childrenLines, "children2", pId));
        return poms;
    }

    private Pom createChildrenPom(String lines, String aId, String pId) {
        return Pom.builder()
                .withArtifactId(aId)
                .withGroupId("gId")
                .withFilePath("pom.xml")
                .withParentArtifactId(pId)
                .withLines(Arrays.asList(lines.split("\n")))
                .build();
    }

    private Pom createPom(String lines, String aId) {
        return Pom.builder()
                .withArtifactId(aId)
                .withGroupId("gId")
                .withFilePath("pom.xml")
                .withLines(Arrays.asList(lines.split("\n")))
                .build();
    }
}