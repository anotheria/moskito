package net.anotheria.moskito.core.config.tagging;

/**
 * Possible tag prefixes, i.e. sources of tag values.
 * @author strel
 */
public enum TagPrefix {

    /**
     * Nothing.
     */
    NONE(""),

    /**
     * HTTP header.
     */
    HEADER("header"),

    /**
     * HTTP request attribute.
     */
    REQUEST("request"),

    /**
     * HTTP session attribute.
     */
    SESSION("session"),

    /**
     * HTTP request parameter.
     */
    PARAMETER("parameter");


    /**
     * All possible prefixes.
     */
    public static final TagPrefix[] PREFIXES = new TagPrefix[] {
        HEADER, REQUEST, SESSION, PARAMETER
    };

    /**
     * Prefix name.
     */
    private String name;


    TagPrefix(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Searches for prefix by given name.
     * @param name Prefix name
     * @return Prefix with given name or none, if no such
     */
    public static TagPrefix findPrefixByName(String name) {
        for (TagPrefix prefix : values()) {
            if (prefix.getName().equals(name)) {
                return prefix;
            }
        }

        return NONE;
    }

    @Override
    public String toString() {
        return "TagPrefix{" +
                "name='" + name + '\'' +
                '}';
    }
}
