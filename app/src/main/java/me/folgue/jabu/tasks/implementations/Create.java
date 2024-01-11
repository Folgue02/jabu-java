package me.folgue.jabu.tasks.implementations;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import me.folgue.jabu.cli.CliParameters;
import me.folgue.jabu.configuration.Configuration;
import me.folgue.jabu.configuration.ProjectType;
import me.folgue.jabu.configuration.projectschema.ProjectSchema;
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
public class Create implements Task, ProjectSchema.CreationHook {

    public static record CreateTaskConfig(
            String name,
            ProjectType type) {

        public static CreateTaskConfig ofCommandLine(CommandLine parsedArgs) {
            return new CreateTaskConfig(
                    parsedArgs.getOptionValue("name"),
                    ProjectType.ofString(parsedArgs.getOptionValue("type")));
        }

    }
    private static final List<TaskDependencySpec> DEPENDENCY_SPECS = new ArrayList<>();

    @Override
    public void run(CliParameters superConfig, List<String> args) throws ParseException, Exception {
        Options options = new Options();

        options.addOption(Option.builder()
                .option("n")
                .longOpt("name")
                .required(true)
                .hasArg()
                .desc("Name of the new project.")
                .type(String.class)
                .build()
        );
        options.addOption("h", "help", false, "Displays a help message.");
        options.addOption(
                Option.builder()
                    .option("t")
                    .longOpt("type")
                    .required(true)
                    .hasArg(true)
                    .desc("What type should the project be.")
                    .type(ProjectType.class)
                    .build()
        );

        CommandLine parsedArgs = new DefaultParser().parse(options, (String[])args.toArray(String[]::new));

        this.run(superConfig, CreateTaskConfig.ofCommandLine(parsedArgs));
    }

    public void run(CliParameters superConfig, CreateTaskConfig taskConfig) throws Exception {
        final Path projectDir = Path.of(superConfig.directory, taskConfig.name());
        final Path jabuFilePath = Path.of(projectDir.toString(), "jabu.json");
        ProjectType projectType = taskConfig.type();
        ProjectSchema projectSchema = ProjectSchema.ofType(projectType);

        if (projectSchema == null)
            throw new Exception("No/Invalid project type specified");

        System.out.println("=> Creating dirs...");
        projectSchema.create(projectDir.toString(), this, null);

        var config = Configuration.defaultOfType(taskConfig.type, taskConfig.name);
        config.writeToFile(jabuFilePath.toFile());
    }

    @Override
    public String getDescription() {
        return "Creates a new project in a folder of the same name as the project.";
    }

    @Override
    public List<TaskDependencySpec> getDependencySpecifications() {
        return DEPENDENCY_SPECS;        
    }

    @Override
    public void beforeCreationHook(int index, File targetDir) {
        System.out.printf("[%d]: Creating directory '%s'...\n", index, targetDir.toString());
    }
}
