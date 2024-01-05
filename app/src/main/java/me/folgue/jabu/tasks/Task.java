package me.folgue.jabu.tasks;

import java.util.*;
import me.folgue.jabu.cli.CliParameters;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

/**
 * A runnable task, such as {@code build}, {@code run} etc...
 * @author folgue
 */
public interface Task {
    /**
     * @return A description of the task itself.
     */
    public String getDescription();
    /**
     * Executes the task (<i>doesn't mean, that this executes the task's dependencies
     * </i>).
     * @param superConfig Configuration of the main execution.
     * @param args Arguments for the execution of the task.
     * @throws Exception Thrown by the task itself (<i>depends on the task</i>).
     * @throws ParseException If the provided arguments to the task are not valid. 
     */
    public void run(CliParameters superConfig, String[] args) throws Exception, ParseException;

    /**
     * @return An ordered list of specified tasks to be executed before executing this
     * task. (<i>these ones <b>must</b> be executed in order</i>)
     */
    public List<TaskDependencySpec> getDependencySpecifications();
}
