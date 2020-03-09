package com.lindenau.control;

import com.lindenau.entity.Pom;

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

    Pattern parentPattern = Pattern.compile(".*<parent>(.*)</parent>.*", Pattern.DOTALL);

    private String getAttribute(List<String> lines, String attributeName) {
        String attribute = "<" + attributeName + ">";
        for (String line : lines) {
            String processLine = line.trim();
            if (processLine.startsWith(attribute)) {
                Pattern attributePattern = Pattern.compile(attribute + "(.*)" + "</" + attributeName + ">");
                Matcher matcher = attributePattern.matcher(processLine);
                if (matcher.matches()) {
                    return matcher.group(1);
                }
            }
        }
        throw new RuntimeException("no " + attribute + " section in pom");
    }

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
        return getAttribute(lines, "artifactId");
    }

    private String getGroupId(List<String> lines) {
        return getAttribute(lines, "groupId");
    }

    private String getParentArtifactId(List<String> lines) {
        String pomContent = String.join("\n", lines);
        Matcher matcher = parentPattern.matcher(pomContent);
        if (matcher.matches()) {
            String parentSection = matcher.group(1);
            return getAttribute(Arrays.asList(parentSection.split("\n")), "artifactId");
        }
        return "";
    }
}
