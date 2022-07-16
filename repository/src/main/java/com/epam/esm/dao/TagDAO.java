package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * The interface provides methods to work with tags information
 * in a data source.
 */
public interface TagDAO {

    /**
     * Find list of all tags
     *
     * @return the list of tags
     */
    List<Tag> findAll();

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
    void deleteById(int id);
}