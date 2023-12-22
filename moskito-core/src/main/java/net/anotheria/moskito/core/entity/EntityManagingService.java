package net.anotheria.moskito.core.entity;

/**
 * The {@code EntityManagingService} interface provides methods for managing entities.
 * Implementing classes should handle operations related to entity management, such as counting entities.
 *
 * @author asamoilich
 */
public interface EntityManagingService {

    /**
     * Gets the count of entities with the specified name.
     *
     * @param entityName The name of the entity for which to retrieve the count.
     * @return The count of entities with the specified name.
     */
    int getEntityCount(String entityName);
}
