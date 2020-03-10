package com.lindenau.control;

import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DependencyLoaderTest {

    private DependencyLoader dependencyLoader = new DependencyLoader();

    @Test
    public void shouldLoadPomDependencies() {
        assertEquals(2, dependencyLoader.loadPomDependencies(createPom()).size());
    }

    @Test
    public void shouldAddSpecialPart() {
        Dependency dependency = dependencyLoader.loadPomDependencies(createPom()).get("concordion");
        assertEquals(1, dependency.getSpecial().split("\n").length);
        assertEquals("<scope>test</scope>", dependency.getSpecial().split("\n")[0]);
    }

    @Test
    public void shouldLoadParentDependenciesFromDependencyManagement() {
        Pom parentPom = createPomWithDependencyManagement();
        Map<String, Dependency> deps = dependencyLoader.loadDependencyManagement(parentPom);
        Assert.assertEquals(2, deps.size());
    }

    @Test(expected = RuntimeException.class)
    public void shouldFailWhenNoDependencyManagementSection() {
        Pom parentPom = createPom();
        dependencyLoader.loadDependencyManagement(parentPom);
    }

    private Pom createPom() {
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
        return Pom.builder()
                .withArtifactId("aId")
                .withGroupId("gId")
                .withFilePath("pom.xml")
                .withLines(Arrays.asList(lines.split("\n")))
                .build();
    }

    private Pom createPomWithDependencyManagement() {
        String lines = "<dependencyManagement>\n<dependencies>\n" +
                "        <dependency>\n" +
                "            <groupId>org.slf4j</groupId>\n" +
                "            <artifactId>slf4j-api</artifactId>\n" +
                "        </dependency>\n" +
                "        <dependency>\n" +
                "            <groupId>org.concordion</groupId>\n" +
                "            <artifactId>concordion</artifactId>\n" +
                "            <scope>test</scope>\n" +
                "        </dependency>\n"+
                "</dependencies>\n</dependencyManagement>";
        return Pom.builder()
                .withArtifactId("aId")
                .withGroupId("gId")
                .withFilePath("pom.xml")
                .withLines(Arrays.asList(lines.split("\n")))
                .build();
    }

}