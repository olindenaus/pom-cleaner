package com.lindenau.control;

import com.lindenau.entity.Pom;
import com.lindenau.entity.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyReader {

    private boolean readProperties = false;
    private Pattern property = Pattern.compile("<(.*)>(.*)</.*");

    public List<Property> readAllProperties(Pom pom) {
        List<Property> properties = new ArrayList<>();
        for (String line : pom.getLines()) {
            if (line.trim().contains("properties>")) {
                readProperties = !readProperties;
                continue;
            }
            if (readProperties) {
                String trimmed = line.trim();
                Matcher matcher = property.matcher(trimmed);
                if (matcher.matches()) {
                    properties.add(new Property(matcher.group(1), matcher.group(2)));
                } else {
                    System.out.println("Line: " + line + " does not match the pattern");
                }
            }
        }
        return properties;
    }
}
