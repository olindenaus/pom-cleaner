package com.lindenau.control;

import com.lindenau.entity.Dependency;
import com.lindenau.entity.Pom;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

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

}