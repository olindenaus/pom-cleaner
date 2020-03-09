package com.lindenau.control;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.*;

class PomLoaderTest {

    @Test
    public void shouldGetTwoPomFiles() {
        createDirectoryWithPoms();
        PomLoader pomLoader = new PomLoader("testproject");
        File[] files = pomLoader.getAllPoms();
        assertEquals(2, files.length);
        try {
            FileUtils.deleteDirectory(new File("testproject"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDirectoryWithPoms() {
        new File("testproject/module").mkdirs();
        createPom("testproject");
        createPom("testproject/module");
    }

    private void createPom(String directory) {
        try (PrintWriter writer = new PrintWriter(directory + "/pom.xml", "UTF-8")) {
            writer.println("sth");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println("could not create " + directory + "/pom.xml");
            e.printStackTrace();
        }
    }
}