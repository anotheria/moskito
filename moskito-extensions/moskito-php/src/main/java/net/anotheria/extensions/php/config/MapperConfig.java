package net.anotheria.extensions.php.config;

import org.configureme.annotations.ConfigureMe;

/**
 * Configuration for producer mapper.
 *
 * Defines mapper class to be loaded by plugin.
 * Mapper be available by configured mapper id.
 *
 * mappers should implement {@link net.anotheria.extensions.php.mappers.Mapper}
 * class to be used.
 *
 */
@ConfigureMe(allfields = true)
public class MapperConfig {

    /**
     * Mapper class canonical name
     */
    private String mapperClass;
    /**
     * Id of mapper
     */
    private String mapperId;

    public String getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(String mapperClass) {
        this.mapperClass = mapperClass;
    }

    public String getMapperId() {
        return mapperId;
    }

    public void setMapperId(String mapperId) {
        this.mapperId = mapperId;
    }

}
