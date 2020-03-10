package com.lindenau.xml;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class XmlReaderTest {

    private List<String> lines;
    private XmlReader xmlReader = new XmlReader();

    @Before
    public void setup() {
        lines = Arrays.asList("<artifactId>a</artifactId>", "<groupId>g</groupId>");
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenNoAttributeFound() {
        xmlReader.getAttribute(lines, "version");
    }

    @Test
    public void shouldFindAttributeValueIfPresent() {
        String artifactId = xmlReader.getAttribute(lines, "artifactId");
        Assert.assertEquals("a", artifactId);
    }

}