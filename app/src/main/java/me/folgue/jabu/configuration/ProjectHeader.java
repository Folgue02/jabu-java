package me.folgue.jabu.configuration;

/**
 * Represents the main attributes of the project, such as the name, the version,
 * the description...
 * @author folgue
 */
public record ProjectHeader(
        String name,
        String description,
        String author,
        String version,
        ProjectType projectType) {
}
