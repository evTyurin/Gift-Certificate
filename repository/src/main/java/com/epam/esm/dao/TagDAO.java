package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

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
     * Find list of tags by gift certificate id
     *
     * @param giftCertificateId the gift certificate id
     * @return the list of tags
     */
    List<Tag> findAll(int giftCertificateId);

    /**
     * Find tag by id
     *
     * @param id the tag id
     * @return the tag entity
     */
    Tag findById(int id);

    /**
     * Find tag by id
     *
     * @param name the tag id
     * @return the tag entity
     */
    Tag findByName(String name);

    /**
     * Find tag id by name
     *
     * @param name the tag name
     * @return the tag id
     */
    int findId(String name);

    /**
     * Create tag by name
     *
     * @param name the tag name
     */
    void create(String name);

    /**
     * Create tag by id and by name
     *
     * @param id   the tag id
     * @param name the tag name
     */
    void create(int id, String name);

    /**
     * Delete tag by id
     *
     * @param id the tag id
     * @return true in case of success, false if not
     */
    boolean deleteById(int id);

    /**
     * Add connection between tad and gift certificate
     *
     * @param certificateId the gift certificate id
     * @param tagId         the tag id
     * @return true in case of success, false if not
     */
    boolean addTagCertificateConnection(int certificateId, int tagId);

    /**
     * Delete connection between tad and gift certificate
     *
     * @param certificateId the gift certificate id
     * @param tagId         the tag id
     * @return true in case of success, false if not
     */
    boolean deleteTagCertificateConnection(int certificateId, int tagId);

    /**
     * Delete all connections between tad and gift certificates
     *
     * @param tagId the tag id
     * @return true in case of success, false if not
     */
    boolean deleteTagCertificateConnection(int tagId);

    /**
     * Check if connection between tad and gift certificate exist
     *
     * @param certificateId the gift certificate id
     * @param tagId         the tag id
     * @return true in case of success, false if not
     */
    boolean ifExistTagCertificateConnection(int certificateId, int tagId);

    /**
     * Find list of tags id by certificate id
     *
     * @param certificateId the gift certificate id
     * @return list of tags id
     */
    List<Integer> findTagsId(int certificateId);
}