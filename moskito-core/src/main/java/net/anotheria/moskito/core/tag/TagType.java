package net.anotheria.moskito.core.tag;

/**
 * Tag type.
 * @author strel
 */
public enum TagType {

    /**
     * Builtin tag.
     */
    BUILTIN("builtin"),

    /**
     * Configured tag.
     */
    CONFIGURED("configured"),

    /**
     * Configured tag.
     */
    ANNOTATED("annotated");

    /**
     * Tag type name.
     */
    private final String name;


    TagType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
