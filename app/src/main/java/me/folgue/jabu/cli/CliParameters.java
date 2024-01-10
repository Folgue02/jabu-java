package me.folgue.jabu.cli;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author folgue
 */
public class CliParameters {
    public String directory;
    public Optional<String> taskToRun;
    public File jabuFile;
    public List<String> taskArgs = new ArrayList<>();


    protected CliParameters(String directory, Optional<String> taskToRun, File jabuFile, List<String> taskArgs) {
        this.directory = directory;
        this.taskToRun = taskToRun;
        this.jabuFile = jabuFile;
        this.taskArgs = taskArgs;
    }

    public CliParameters(String[] args) throws ParseException {
        this.directory = System.getProperty("user.dir");
        this.taskToRun = ((List<String>)Arrays.asList(args)).stream().findFirst();
        this.jabuFile = Path.of(this.directory, "jabu.json").toFile();

        if (args.length > 1) {
            String[] newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, newArgs.length);
            this.taskArgs = (List<String>)Arrays.asList(newArgs);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (other instanceof CliParameters cliOther) {
            return this.taskArgs.equals(cliOther.taskArgs)
                && this.jabuFile.equals(cliOther.jabuFile)
                && this.taskToRun.equals(cliOther.taskToRun)
                && this.directory.equals(cliOther.directory);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
    //String directory, Optional<String> taskToRun, File jabuFile, List<String> taskArgs
        return String.format("Directory: %s, Task to run: %s, Jabufile: %s, Task's args: %s", this.directory, this.taskToRun, this.jabuFile, this.taskArgs);
    }
}
