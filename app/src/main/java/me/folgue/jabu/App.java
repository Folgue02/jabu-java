package me.folgue.jabu;

import me.folgue.jabu.cli.CliParameters;

public class App {
    public static final String VERSION = "0.0.1";
    public static void main(String[] args) throws Exception {
        CliParameters cliConfig;
        try {
            cliConfig = new CliParameters(args);
        } catch (CliParameters.HelpMessage e) {
            System.exit(0);
            return;
        } catch (CliParameters.VersionRequested e) {
            displayEnv();
            System.exit(0);
            return;
        } catch (CliParameters.NoTask e) {
            System.err.println("You have to specify a task.");
            System.exit(1);
            return;
        } catch (CliParameters.InvalidDirectory e) {
            System.err.println("The specified directory doesn't exist or is invalid.");
            System.exit(1);
            return;
        }
        System.out.println(cliConfig);
        System.out.println(cliConfig.tasks);
    }

    public static void displayEnv() {
        System.out.printf("Jabu v%s running on %s %s\n", VERSION, System.getProperty("os.name"), System.getProperty("os.version"));
    }
}