package me.folgue.jabu.configuration;

import me.folgue.jabu.configuration.projectschema.ProjectSchema;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.HashMap;
import java.io.IOException;
import java.util.Map;

/**
 * Represents the configuration that can be found in a {@code jabu.json} file.
 * This contains:<br/>
 * <ul>
 *   <li>The configuration for compiling and running the project with java.</li>
 *   <li>The main information about the project (<i>the {@link ProjectHeader}</i>)</li>
 *   <li>Schema of the file structure of the project (<i>{@link ProjectSchema}</i>).</li>
 *   <li>And the properties related to the project (<i> a simple {@link HashMap} 
 *   of keys pointing to values</i>).</li>
 * </ul>
 * @author folgue
 */
public record Configuration(
        JavaConfig javaConfig,
        ProjectHeader header,
        ProjectSchema fsSchema,
        Map<String, String> propertiesMap) {

    /**
     * Parses the given file and returns an instance of {@link Configuration}.
     * @param file File to be read and parsed.
     * @return An instance of {@link Configuration} containing the data of the file.
     * @throws IOException If any problems occur related to accessing the file.
     * @throws JsonParseException If the contents of the json file invalid.
     */
    public static Configuration ofFile(File file) throws IOException, JsonParseException {
        var objectMapper = new ObjectMapper();
        JsonNode tree = objectMapper.readTree(file);
        return objectMapper.treeToValue(tree, Configuration.class);
    }

    /**
     * Writes the state into a given file.
     * @param file File to write to.
     * @throws IOException If any problems related to IO occur.
     * @throws DatabindException
     * @throws StreamWriteException 
     */
    public void writeToFile(File file) throws IOException, DatabindException, StreamWriteException {
        var objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(file, this);
    }

    /**
     * Creates a default configuration based on the given type.
     * @param type Type of the project.
     * @param name Name of the project
     * @return Valid configuration for that type of project.
     */
    public static Configuration defaultOfType(ProjectType type, String name) {
        switch (type) {
            case Binary ->  {
                var javaConfig = JavaConfig.defaultOfVersion(Runtime.version());
                var header = new ProjectHeader(name, "A java project.", "anon", "0.0.1", type);
                return new Configuration(javaConfig, header, ProjectSchema.ofType(type), Map.of("mainClass", ""));
            }
            case Library -> {
                var javaConfig = JavaConfig.defaultOfVersion(Runtime.version());
                var header = new ProjectHeader(name, "A java library.", "anon", "0.0.1", type);
                return new Configuration(javaConfig, header, ProjectSchema.ofType(type), new HashMap<>());
            }
            default -> throw new IllegalArgumentException("Unrecognized type: " + type +", for project of name: " + name);
        }
    }
}
