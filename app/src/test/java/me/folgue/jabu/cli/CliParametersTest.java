package me.folgue.jabu.cli;

import me.folgue.jabu.cli.CliParameters;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;

import org.apache.commons.cli.ParseException;
import java.util.*;

import org.junit.jupiter.api.DisplayName;

public class CliParametersTest {
    @Test
    @DisplayName("Parsing args with an specified task as well as its arguments.")
    public void parseArgsWithTask() throws ParseException {
        String[] args = {
            "run",
            "--main-class",
            "--something"
        };
        var expected = new CliParameters(
                System.getProperty("user.dir"),
                Optional.of("run"),
                Path.of(System.getProperty("user.dir"), "jabu.json").toFile(),
                List.of("--main-class", "--something"));

        assertEquals(expected, new CliParameters(args));
    }

    @Test
    @DisplayName("Parse arguments without an specified task (empty args)")
    public void parseArgsWithoutTask() throws ParseException {
        String[] args = {};
        var expected = new CliParameters(
                System.getProperty("user.dir"),
                Optional.empty(),
                Path.of(System.getProperty("user.dir"), "jabu.json").toFile(),
                new ArrayList<>());

        assertEquals(expected, new CliParameters(args));
    }
}
