package me.folgue.jabu.jtools;

import me.folgue.jabu.configuration.JavaConfig;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import org.junit.jupiter.api.DisplayName;

public class JavacTest {
    @Test
    @DisplayName("Test only specifying sources.")
    void testOnlySources() {
        var expected = List.of(
                "File.java",
                "com/company/App.java",
                "com/company/utils/FileTypes.java");
        var sources = List.of("File.java", "com/company/App.java", "com/company/utils/FileTypes.java");
        var result = new Javac(sources, Optional.empty(), Optional.empty());

        assertEquals(expected, result.getArgs());
    }

    @Test
    @DisplayName("Test with sources and output directory.")
    void testSourcesWithOutputDirectory() {
        var expected = List.of(
                "File.java",
                "com/company/App.java",
                "com/company/utils/FileTypes.java",
                "-d", "outputdir");
        var sources = List.of("File.java", "com/company/App.java", "com/company/utils/FileTypes.java");
        var result = new Javac(sources, Optional.of("outputdir"), Optional.empty());

        assertEquals(expected, result.getArgs());
    }

    @Test
    @DisplayName("Test with sources, output directory and java config.")
    void testWithOutputDirectoryAndJavaConfig() {
        var expected = List.of(
                "File.java",
                "com/company/App.java",
                "com/company/utils/FileTypes.java",
                "-d", "outputdir", 
                "-source", "17",
                "-target", "17");
        var sources = List.of("File.java", "com/company/App.java", "com/company/utils/FileTypes.java");
        var result = new Javac(sources, Optional.of("outputdir"), Optional.of(JavaConfig.defaultOfVersion(17)));

        assertEquals(expected, result.getArgs());
    }
}
