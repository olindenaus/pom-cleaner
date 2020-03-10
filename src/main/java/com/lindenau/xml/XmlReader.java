package com.lindenau.xml;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlReader {

    public String getAttribute(List<String> lines, String attributeName) {
        String attribute = getOptionalAttribute(lines, attributeName);
        if (attribute.isEmpty()) {
            throw new RuntimeException("no " + attribute + " section in specified lines\n" + lines);
        }
        return attribute;
    }

    public String getOptionalAttribute(List<String> lines, String attributeName) {
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
        return "";
    }
}
