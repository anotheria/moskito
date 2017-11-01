package net.anotheria.moskito.core.config.tagging;

/**
 * Possible tag types, i.e. sources of tag values.
 * @author strel
 */
public enum TagType {

    /**
     * Nothing.
     */
    NONE(""),

    /**
     * Builtin, JVM / Java API for example.
     */
    BUILTIN("builtin"),

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
     * All possible types.
     */
    public static final TagType[] TYPES = new TagType[] {
        HEADER, REQUEST, SESSION, PARAMETER
    };

    /**
     * Type name.
     */
    private String name;


    TagType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Searches for type by given name.
     * @param name Type name
     * @return Type with given name or none, if no such
     */
    public static TagType findTagTypeByName(String name) {
        for (TagType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        return NONE;
    }

    @Override
    public String toString() {
        return "TagType{" +
                "name='" + name + '\'' +
                '}';
    }
}
