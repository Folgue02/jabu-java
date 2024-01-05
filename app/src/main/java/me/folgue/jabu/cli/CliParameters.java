package me.folgue.jabu.cli;

import java.io.File;
import java.nio.file.Path;
import java.util.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author folgue
 */
public class CliParameters {
    public static abstract class CliEvent extends Exception {}
    public static class NoTask extends CliEvent {}
    public static class InvalidDirectory extends CliEvent{}
    public static class HelpMessage extends CliEvent {}
    public static class VersionRequested extends CliEvent {}

    public String directory;
    public List<String> tasks;
    public String jabuFile;


    public CliParameters(String[] args) throws ParseException, CliEvent {
        Options options = new Options();
        
        Option[] parameters = {
            Option.builder()
                .longOpt("directory")
                .option("d")
                .hasArg(true)
                .desc("Directory to work on.")
                .build(),
            Option.builder()
                .option("h")
                .longOpt("help")
                .hasArg(false)
                .desc("Displays a help message.")
                .build(),
            Option.builder()
                .option("f")
                .longOpt("jabu-file")
                .hasArg(true)
                .desc("Jabu build configuration file.")
                .build(),
            Option.builder()
                .option("v")
                .longOpt("version")
                .hasArg(false)
                .desc("Displays the version of Jabu.")
                .build()
        };
        
        for (var opt : parameters)
            options.addOption(opt);
        
        CommandLine parsedArgs = new DefaultParser().parse(options, args);
        this.initialize(parsedArgs, options);
    }
    
    private void initialize(CommandLine parsedArgs, Options options) throws CliEvent { 
        // -h
        if (parsedArgs.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("jabu [tasks...] [options...]", options);
            throw new HelpMessage();
        }

        // -v
        if (parsedArgs.hasOption("version"))
            throw new VersionRequested();

        if (parsedArgs.getArgList().isEmpty())
            throw new NoTask();
        else
            this.tasks = parsedArgs.getArgList();
        
        // -d
        if (parsedArgs.hasOption("directory")) {
            this.directory = parsedArgs.getOptionValue("directory");
        } else {
            // The default directory is the cwd
            this.directory = System.getProperty("user.dir");
        }

        // -f
        if (parsedArgs.hasOption("jabu-file)")) {
            this.jabuFile = parsedArgs.getOptionValue("jabu-file");
        } else {
            this.jabuFile = Path.of(this.directory, "jabu.json").toString();
        }
        
        if (!new File(this.directory).isDirectory())
            throw new InvalidDirectory();
    }

    @Override
    public String toString() {
        return "CliParameters{" + "directory=" + directory + ", task=" + tasks + ", jabuFile=" + jabuFile + '}';
    }
}