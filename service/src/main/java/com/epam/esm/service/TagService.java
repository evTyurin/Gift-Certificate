package com.epam.esm.service;

import com.epam.esm.exception.EntityExistException;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotFoundException;

import java.util.List;

/**
 * The TagService is interface that contains CRD methods to work with Tags
 */
public interface TagService {

    /**
     * Find list of all tags
     *
     * @return the list of tags
     */
    List<Tag> findAll();

    /**
     * Find tag by id
     *
     * @param tagId the tag id
     * @return the tag entity
     * @throws NotFoundException indicates that tag with this id not exist
     */
    Tag find(int tagId) throws NotFoundException;

    /**
     * Create new Tag
     *
     * @param tag the tag entity
     * @throws EntityExistException indicates that tag with this name already exist
     */
    void create(Tag tag) throws EntityExistException;

    /**
     * Delete tag by id
     *
     * @param tagId the tag id
     * @throws NotFoundException indicates that tag with this id not exist
     */
    void delete(int tagId) throws NotFoundException;
}