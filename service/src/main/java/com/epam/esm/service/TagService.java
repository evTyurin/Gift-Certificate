package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;

import java.util.List;

/**
 * The TagService is interface that contains CRD methods to work with Tags
 */
public interface TagService {

    /**
     * Find list of all tags
     *
     * @param pageNumber        the number of page being viewed
     * @param pageElementAmount amount of elements per page
     * @return the list of tags
     */
    List<Tag> findAll(int pageNumber, int pageElementAmount) throws
            PageElementAmountException,
            PageNumberException;

    /**
     * Find tag by id
     *
     * @param id the tag id
     * @return the tag entity
     * @throws NotFoundException indicates that tag with this id not exist
     */
    Tag find(int id) throws NotFoundException;

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
     * @param id the tag id
     * @throws NotFoundException indicates that tag with this id not exist
     */
    void delete(int id) throws NotFoundException;

    /**
     * Get amount of all tags in database
     *
     * @return number of all tags in database
     */
    int findAmount();

    /**
     * Get list all most widely used tags in database
     *
     * @return number of all most widely used tags in database
     */
    List<Tag> findMostWidelyUsedTag();
}