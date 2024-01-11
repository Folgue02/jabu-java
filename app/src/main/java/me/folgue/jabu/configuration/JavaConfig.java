package me.folgue.jabu.configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Configuration for Java, used for determining on how to compile, run a project.
 * @author folgue
 */
public record JavaConfig(int javaVersion, int sourceVersion, int targetVersion) {
    @JsonIgnore
    public boolean isValid() {
        return javaVersion >= sourceVersion
                && javaVersion >= targetVersion
                && targetVersion >= sourceVersion;
    }

    /**
     * Creates a default java config based on the provided version.
     * @param javaVersion Version of the Java JDK
     * @return A {@link JavaConfig} object which all java versions (<i>the JRE, source
     * and target versions</i>) are the same (<i>the one provided by arguments</i>)
     */
    public static JavaConfig defaultOfVersion(Runtime.Version javaVersion) {
        int version = javaVersion.feature() == 1 ? 
                Runtime.version().interim() 
                : Runtime.version().feature();

        return new JavaConfig(version, version, version);
    }

    /**
     * Instantiates a {@link JavaConfig} object with every version set to 
     * the one specified by arguments.
     * @param javaVersion Version.
     */
    public static JavaConfig defaultOfVersion(int javaVersion) {
        return new JavaConfig(javaVersion, javaVersion, javaVersion);
    }
}
