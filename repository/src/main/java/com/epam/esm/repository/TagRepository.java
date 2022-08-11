package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface provides methods to work with tags information
 * in a data source.
 */
public interface TagRepository {

    List<Tag> findMostWidelyUsedTag();

    /**
     * Find list of all tags
     *
     * @param pageNumber        the number of page being viewed
     * @param pageElementAmount amount of elements per page
     * @return the list of tags
     */
    List<Tag> findAll(int pageNumber, int pageElementAmount);

    /**
     * Find tag by id
     *
     * @param id the tag id
     * @return the tag entity
     */
    Optional<Tag> find(int id);

    /**
     * Find tag by id
     *
     * @param name the tag id
     * @return the tag entity
     */
    Optional<Tag> find(String name);

    /**
     * Create tag by name
     *
     * @param name the tag name
     */
    void create(String name);

    /**
     * Delete tag by id
     *
     * @param id the tag id
     */
    void delete(int id);

    /**
     * Get amount of all tags in database
     *
     * @return number of all tags in database
     */
    int findAmount();
}