package me.folgue.jabu.configuration;

/**
 * Represents the types of projects.
 * @author folgue
 */
public enum ProjectType {
    Binary,
    Library;
    
    /**
     * Returns a variant of the {@link ProjectType} enum. If the provided string
     * doesn't match any of the variants, {@code null} gets returned.
     * @param rawString String to be checked (<i>case insensitive</i>)
     * @return Enum variant corresponding to the given string, or {@code null} if
     * the string doesn't correspond to any of the variants.
     */
    public static ProjectType ofString(String rawString) {
        return switch (rawString.toLowerCase()) {
            case "binary" -> Binary;
            case "library" -> Library;
            default -> null;
        };
    }
}
