package me.folgue.jabu.jtools;

import java.util.*;
import me.folgue.jabu.configuration.JavaConfig;

public record Javac(
        List<String> sources,
        Optional<String> outputDirectory,
        Optional<JavaConfig> javaConfig) {

    public List<String> getArgs() {
        List<String> args = new ArrayList<>();

        args.addAll(this.sources);

        this.outputDirectory().ifPresent(od -> {
            args.add("-d");
            args.add(od);
        });

        this.javaConfig.ifPresent(jc -> {
            args.add("-source");
            args.add(Integer.toString(jc.sourceVersion()));

            args.add("-target");
            args.add(Integer.toString(jc.targetVersion()));
        });

        return args;
    }


}
