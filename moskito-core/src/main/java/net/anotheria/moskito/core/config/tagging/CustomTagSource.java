package net.anotheria.moskito.core.config.tagging;

/**
 * Possible tag value sources.
 * @author strel
 */
public enum CustomTagSource {

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
     * All possible sources.
     */
    public static final CustomTagSource[] SOURCES = new CustomTagSource[] {
        HEADER, REQUEST, SESSION, PARAMETER
    };

    /**
     * Source name.
     */
    private String name;


    CustomTagSource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Searches for source by given name.
     * @param name Source name
     * @return Source with given name or none, if no such
     */
    public static CustomTagSource findTagSourceByName(String name) {
        for (CustomTagSource source : values()) {
            if (source.getName().equals(name)) {
                return source;
            }
        }

        return NONE;
    }

    @Override
    public String toString() {
        return "CustomTagSource{" +
                "name='" + name + '\'' +
                '}';
    }
}
