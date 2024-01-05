package me.folgue.jabu.tasks;

import org.apache.commons.cli.CommandLine;

/**
 *
 * @author folgue
 */
public record TaskDependencySpec(String taskName, String[] args) {
}
