package me.folgue.jabu.configuration;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author folgue
 */
public record Configuration(
        JavaConfig javaConfig,
        ProjectHeader header) {
    public static Configuration ofFile(File file) throws IOException, JsonParseException {
        var objectMapper = new ObjectMapper();
        JsonNode tree = objectMapper.readTree(file);
        return objectMapper.treeToValue(tree, Configuration.class);
    }
}
