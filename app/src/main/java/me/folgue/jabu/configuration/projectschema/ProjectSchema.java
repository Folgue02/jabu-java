package me.folgue.jabu.configuration.projectschema;

import java.nio.file.*;
import java.io.IOException;
import java.io.File;
import java.util.*;
import me.folgue.jabu.configuration.ProjectType;

/**
 * Schema of a project file structure.
 * @author folgue
 */
public record ProjectSchema(
        String sourceDir, 
        String targetDir,
        String resourceDir,
        List<String> otherDirs) {

    @FunctionalInterface
    public static interface CreationHook {
        /**
         * This method will be called before the creation of the directory.
         * @param dirToBeCreated The directory that will be created.
         */
        void beforeCreationHook(int count, File dirToBeCreated);

        /**
         * @return A {@link CreationHook} that doesn't do anything.
         */
        static CreationHook getDefaultHook() {
            return (i, f) -> {};
        }
    }

    /**
     * @return An immutable {@link List} of the directories of the schema.
     */
    public List<String> getDirs() {
        return List.of(this.sourceDir, this.targetDir, this.resourceDir);
    }

    /**
     * Creates all the schema's directories appended after the {@code base} directory
     * parameter.
     * @param base The directory where the schema's directories will be created in.
     * @throws IOException If any creation fails.
     */
    public void createDirs(String base) throws IOException { 
        this.createDirs(base, CreationHook.getDefaultHook());
    }

    /**
     * Creates all the schema's directories appended after the {@code base} directory
     * parameter.
     * @param base The directory where the schema's directories will be created in.
     * @param hook Method to be called every time before the creation of a directory,
     * this method gets passed the count of directories that have been created, and
     * a {@link File} representing the directory that will be created after the 
     * hook's execution.
     * @throws IOException If any creation fails.
     */
    public void createDirs(String base, CreationHook hook) throws IOException {
        var dirs = this.getDirs().stream()
                .map(d -> Path.of(base, d).toFile()).toList();

        for (int i=0;i < dirs.size();i++) {
            hook.beforeCreationHook(i, dirs.get(i));
            dirs.get(i).mkdirs();
        }
    }

    /**
     * Creates a project schema based on the project's type.
     * @param projectType Type of project
     * @return An instance of {@link ProjectSchema} with the default configuration 
     * for the given project type.
     */
    public static ProjectSchema ofType(ProjectType projectType) {
        return switch (projectType) {
            case Binary, Library -> new ProjectSchema("./src/main/", "./target/", "./src/resources/", List.of());
            default -> null;
        };
    }
}
