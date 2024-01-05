package me.folgue.jabu.configuration;

/**
 *
 * @author folgue
 */
public record JavaConfig(int javaVersion, int sourceVersion, int targetVersion) {
    public boolean isValid() {
        return javaVersion >= sourceVersion
                && javaVersion >= targetVersion
                && targetVersion >= sourceVersion;
    }
}
