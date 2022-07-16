package com.epam.esm.dao;

import com.epam.esm.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

/**
 * The interface provides methods to work with gift certificates information
 * in a data source.
 */
public interface GiftCertificateDAO {

    /**
     * Find gift certificate by id
     *
     * @param id the gift certificate id
     * @return the gift certificate entity
     */
    Optional<GiftCertificate> find(int id);

    /**
     * Find gift certificate by name
     *
     * @param name the gift certificate name
     * @return the gift certificate entity
     */
    Optional<GiftCertificate> find(String name);

    /**
     * Delete gift certificate by id
     *
     * @param id the gift certificate id
     */
    void delete(int id);

    /**
     * Create gift certificate by id
     *
     * @param giftCertificate the gift certificate entity
     */
    void create(GiftCertificate giftCertificate);

    /**
     * Update exist gift certificate by id
     *
     * @param giftCertificate the gift certificate entity used for update
     */
    void update(GiftCertificate giftCertificate);

    /**
     * Find list of gift certificates found/sort by criterion
     *
     * @param query sql query included found/sort criterion
     * @return list of giftCertificate found/sort by criterion
     */
    List<GiftCertificate> findCertificatesByCriterion(String query);
}