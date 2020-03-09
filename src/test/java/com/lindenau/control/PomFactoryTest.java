package com.lindenau.control;

import com.lindenau.entity.Pom;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class PomFactoryTest {

    @Test
    public void shouldMapFileToPom() {
        createPom();
        File file = new File("testproject/pom.xml");
        PomFactory pomFactory = new PomFactory();
        Pom pom = pomFactory.mapToPom(file);
        Assert.assertEquals("aId", pom.getArtifactId());
        Assert.assertEquals("parentAId", pom.getParentArtifactId());
        clean();
    }

    private void createPom() {
        new File("testproject").mkdirs();
        try (PrintWriter writer = new PrintWriter("testproject/pom.xml", "UTF-8")) {
            writer.println("<artifactId>aId</artifactId>");
            writer.println("<groupId>gId</groupId>");
            writer.println("<parent>");
            writer.println("<artifactId>parentAId</artifactId>");
            writer.println("<artifactId>parentGId</groupId>");
            writer.println("</parent>");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("could not create " + "test/pom.xml");
            e.printStackTrace();
        }
    }

    private void clean() {
        try {
            FileUtils.deleteDirectory(new File("testproject"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}