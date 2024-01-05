package me.folgue.jabu.tasks.implementations;

import java.nio.file.Path;
import java.util.*;
import me.folgue.jabu.cli.CliParameters;
import me.folgue.jabu.configuration.ProjectType;
import me.folgue.jabu.tasks.Task;
import me.folgue.jabu.tasks.TaskDependencySpec;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author folgue
 */
public class Create implements Task {

    public static record CreateTaskConfig() {

    }
    private static final List<TaskDependencySpec> DEPENDENCY_SPECS = new ArrayList<>();

    @Override
    public void run(CliParameters superConfig, String[] args) throws ParseException, Exception {
        Options options = new Options();

        options.addOption("d", "directory", true, "Directory where to create the new project.");
        options.addOption("n", "name", true, "Name of the new project.");
        options.addOption("h", "help", false, "Displays a help message.");
        options.addOption(
                Option.builder()
                    .option("t")
                    .longOpt("type")
                    .hasArg(true)
                    .desc("What type should the project be.")
                    .type(ProjectType.class)
                    .build()
        );

        CommandLine parsedArgs = new DefaultParser().parse(options, args);
        this.run(superConfig, parsedArgs);
    }

    public void run(CliParameters superConfig, CommandLine parsedArgs) throws Exception {
        final Path projectDir = Path.of(superConfig.directory, parsedArgs.);
    }

    @Override
    public String getDescription() {
        return "Creates a new project in a folder of the same name as the project.";
    }

    @Override
    public List<TaskDependencySpec> getDependencySpecifications() {
        return DEPENDENCY_SPECS;        
    }
}