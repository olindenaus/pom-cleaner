package com.lindenau.control;

import com.lindenau.entity.Pom;
import com.lindenau.xml.XmlReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PomReader {

    private Pattern parentPattern = Pattern.compile(".*<parent>(.*)</parent>.*", Pattern.DOTALL);
    private XmlReader xmlReader = new XmlReader();

    public Pom readPomFile(File file) {
        System.out.println("Reading pom: " + file.getName());
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(file.getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Pom.builder()
                .withArtifactId(getArtifactId(lines))
                .withGroupId(getGroupId(lines))
                .withParentArtifactId(getParentArtifactId(lines))
                .withFilePath(file.getPath())
                .withLines(lines)
                .build();
    }

    private String getArtifactId(List<String> lines) {
        return xmlReader.getAttribute(lines, "artifactId");
    }

    private String getGroupId(List<String> lines) {
        return xmlReader.getAttribute(lines, "groupId");
    }

    private String getParentArtifactId(List<String> lines) {
        String pomContent = String.join("\n", lines);
        Matcher matcher = parentPattern.matcher(pomContent);
        if (matcher.matches()) {
            String parentSection = matcher.group(1);
            return xmlReader.getAttribute(Arrays.asList(parentSection.split("\n")), "artifactId");
        }
        return "";
    }
}
