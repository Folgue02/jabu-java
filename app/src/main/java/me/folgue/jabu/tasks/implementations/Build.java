package me.folgue.jabu.tasks.implementations;

import me.folgue.jabu.cli.CliParameters;
import me.folgue.jabu.configuration.Configuration;
import me.folgue.jabu.tasks.Task;
import me.folgue.jabu.tasks.TaskDependencySpec;
import me.folgue.jabu.utils.Time;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import me.folgue.jabu.jtools.Javac;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;


public class Build implements Task {
    public static record BuildTaskConfig() {
        public static BuildTaskConfig ofCommandLine(CommandLine parsedArgs) {
            return new BuildTaskConfig();
        }
    }

    private static final List<TaskDependencySpec> DEPENDENCY_SPECS = List.of();

    @Override
    public String getDescription() {
        return "Builds the current project";
    }

    @Override
    public List<TaskDependencySpec> getDependencySpecifications() {
        return DEPENDENCY_SPECS;
    }

    private static Options getOptions() {
        var options = new Options();
        return options;
    }

    @Override
    public void run(CliParameters superConfig, List<String> args) throws Exception {
        CommandLine parsedArgs = new DefaultParser().parse(getOptions(), args.toArray(String[]::new));
        this.run(superConfig, BuildTaskConfig.ofCommandLine(parsedArgs)); 
    }

    public void run(CliParameters superConfig, BuildTaskConfig buildConfig) throws Exception {
        if (!superConfig.isEnvironmentValid())
            throw new IllegalArgumentException("Either the jabu file doesn't exist, or jabu is being executed with a directory that doesn't.");

        Configuration config = Configuration.ofFile(superConfig.jabuFile);

        List<String> javaSources = getJavaSources(Path.of(superConfig.directory, config.fsSchema().sourceDir())).stream()
                .map(Path::toString)
                .toList();

        Javac javaCompilerConfig = new Javac(
                javaSources, 
                Optional.of(config.fsSchema().targetDir()), 
                Optional.of(config.javaConfig()));

        Time.RunnableResults<Integer> results = compileJavaSources(javaCompilerConfig);
        

        if (results.result() != 0) {
            System.err.printf("Something has gone wrong while compiling java sources. exit code: %d\n", results.result());
        } else {
            // FIX: Always displays 0.000
            System.out.printf("Done. (Total time: %.3fs)\n", results.result() / 1000f);
        }
    }

    private static Time.RunnableResults<Integer> compileJavaSources(Javac javacConfig) {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();

        System.out.printf("Compiling java with javac (arguments: %s)\n", javacConfig.getArgs());
        return Time.measureTime(() -> javac.run(null, null, null, javacConfig.getArgs().toArray(String[]::new)));
    }

    public List<Path> getJavaSources(Path sourceRootDir) throws IOException {
        sourceRootDir = sourceRootDir.normalize();

        return Files.walk(sourceRootDir)
            .filter(filePath -> {
                return filePath.toString().endsWith(".java");
            })
            .toList();
    }
}
