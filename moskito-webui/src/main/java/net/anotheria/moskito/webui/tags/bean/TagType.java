package net.anotheria.moskito.webui.tags.bean;

/**
 * Tag type.
 * @author strel
 */
public enum TagType {

    /**
     * Nothing.
     */
    NONE(""),

    /**
     * Builtin tag.
     */
    BUILTIN("builtin"),

    /**
     * Configured tag.
     */
    CONFIGURED("configured");

    /**
     * All possible tag types.
     */
    public static final TagType[] TYPES = new TagType[] {
            BUILTIN, CONFIGURED
    };

    /**
     * Tag type name.
     */
    private String name;


    TagType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Searches for tag type by given name.
     * @param name Tag type name
     * @return Tag type with given name or none, if no such
     */
    public static TagType findTagTypeByName(String name) {
        for (TagType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        return NONE;
    }
}
