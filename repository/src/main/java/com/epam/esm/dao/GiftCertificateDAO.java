package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;

/**
 * The interface provides methods to work with gift certificates information
 * in a data source.
 */
public interface GiftCertificateDAO {

    /**
     * Find list of tags id
     *
     * @param query custom query
     * @return the list of gift certificates id
     */
    List<Integer> findCertificatesIdByParams(String query);

    /**
     * Find gift certificate by id
     *
     * @param id the gift certificate id
     * @return the gift certificate entity
     */
    GiftCertificate find(int id);

    /**
     * Find gift certificate by name
     *
     * @param name the gift certificate name
     * @return the gift certificate entity
     */
    GiftCertificate find(String name);

    /**
     * Delete gift certificate by id
     *
     * @param id the gift certificate id
     * @return true in case of success, false if not
     */
    boolean delete(int id);

    /**
     * Create gift certificate by id
     *
     * @param giftCertificate the gift certificate entity
     * @return true in case of success, false if not
     */
    boolean create(GiftCertificate giftCertificate);

    /**
     * Update exist gift certificate by id
     *
     * @param id              the gift certificate id which need to update
     * @param giftCertificate the gift certificate entity used for update
     * @return true in case of success, false if not
     */
    boolean update(int id, GiftCertificate giftCertificate);

    /**
     * Find gift certificate id by name
     *
     * @param certificateName the gift certificate name
     * @return the gift certificate id
     */
    int findId(String certificateName);
}