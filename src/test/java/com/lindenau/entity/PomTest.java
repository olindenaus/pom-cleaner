package com.lindenau.entity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

public class PomTest {

    @Test
    public void shouldCreatePomEntity() {
        Pom pom = Pom.builder()
                .withArtifactId("artifact-id")
                .withGroupId("groupId")
                .withFilePath("")
                .withLines(Collections.emptyList())
                .build();
        Assert.assertEquals("groupId", pom.getGroupId());
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldFailCreatingPomEntity() {
        exception.expect(NullPointerException.class);
        Pom pom = Pom.builder()
                .withArtifactId("artifact-id")
                .build();
    }

}