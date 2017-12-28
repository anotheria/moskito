package net.anotheria.extensions.php;

import net.anotheria.extensions.php.mappers.Mapper;

import java.util.HashMap;

/**
 * Registry to store stats mappers by their id.
 * Loads it data from plugin configuration
 */
class MappersRegistry {

    private HashMap<String, Mapper> registeredMappers = new HashMap<>();

    /**
     * Registers new mapper
     * @param mapperId new mapper id
     * @param mapper mapper instance
     */
    void registerMapper(String mapperId, Mapper mapper) {
        registeredMappers.put(mapperId, mapper);
    }

    /**
     * Returns mapper by it id
     * @param mapperId id of mapper
     * @return mapper instance or null if no mapper with such id exists
     */
    Mapper getMapper(String mapperId) {
        return registeredMappers.get(mapperId);
    }

}
