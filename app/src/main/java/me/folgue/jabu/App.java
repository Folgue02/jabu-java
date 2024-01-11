package me.folgue.jabu;

import me.folgue.jabu.cli.CliParameters;
import me.folgue.jabu.tasks.TaskRepository;
import me.folgue.jabu.tasks.implementations.Build;
import me.folgue.jabu.tasks.implementations.Create;
import me.folgue.jabu.tasks.Task;
import java.util.*;

import org.apache.commons.cli.ParseException;

public class App {
    public static final String VERSION = "0.0.1";
    public static void main(String[] args) throws Exception {
        var parsedArgs = new CliParameters(args);
        registerDefaultTasks();
        
        parsedArgs.taskToRun.ifPresent(taskName -> {
            Optional<Task> task = TaskRepository.getTask(taskName);

            if (task.isPresent()) {
                try {
                    task.get().run(parsedArgs, parsedArgs.taskArgs);
                } catch (ParseException e) {
                    System.err.println("Invalid arguments: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace(); // TODO: Delete
                    System.err.println("Something went wrong: " + e.getMessage());
                }
            } else {
                System.err.printf("No task with name '%s' found.\n", taskName);
            }
        });
    }

    public static void displayEnv() {
        System.out.printf("Jabu v%s running on %s %s\nJava ", VERSION, System.getProperty("os.name"), System.getProperty("os.version"), Runtime.version());
    }

    public static void registerDefaultTasks() {
        TaskRepository.registerTask("create", new Create());
        TaskRepository.registerTask("build", new Build());
    }

    public static void displayAllTasks() {
    }
}
