package com.lindenau.control;

import com.lindenau.entity.Pom;
import com.lindenau.entity.Property;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PropertyReaderTest {

    PropertyReader propertyReader = new PropertyReader();

    @Test
    public void shouldGetPropertiesFromPom() {
        List<Property> properties = propertyReader.readAllProperties(createPom());
        assertEquals(2, properties.size());
        assertEquals("maven.a", properties.get(0).getName());
        assertEquals("maven.b", properties.get(1).getName());
        assertEquals("1.8", properties.get(0).getValue());
        assertEquals("1.7", properties.get(1).getValue());
    }

    private Pom createPom() {
        String content = "<properties>\n" +
                "<maven.a>1.8</maven.a>\n" +
                "<maven.b>1.7</maven.b>\n" +
                "</properties>";
        return Pom.builder()
                .withLines(Arrays.asList(content.split("\n")))
                .withFilePath("")
                .withGroupId("gid")
                .withArtifactId("a")
                .build();
    }

}