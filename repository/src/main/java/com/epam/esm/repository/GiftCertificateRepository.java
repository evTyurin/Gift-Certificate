package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryCriteria;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * The interface provides methods to work with gift certificates information
 * in a data source.
 */
public interface GiftCertificateRepository {

    /**
     * Find gift certificate by id
     *
     * @param id the gift certificate id
     * @return the gift certificate optional
     */
    Optional<GiftCertificate> find(int id);

    /**
     * Find gift certificate by name
     *
     * @param name the gift certificate name
     * @return the gift certificate optional
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
     * @param searchQueryCriteria criterion used for search
     * @param orderQueryCriteria  criterion used for search
     * @param pageNumber          the number of page being viewed
     * @param pageElementsAmount  amount of elements per page
     * @return list of giftCertificate found/sort by criterion
     */
    List<GiftCertificate> findCertificatesByCriterion(List<QueryCriteria> searchQueryCriteria,
                                                      List<QueryCriteria> orderQueryCriteria,
                                                      int pageNumber,
                                                      int pageElementsAmount);

    /**
     * Get amount of instances that match search parameters
     *
     * @param searchQueryCriteria criterion used for search
     * @param orderQueryCriteria  criterion used for search
     * @return number of instances
     */
    int countByCriterion(List<QueryCriteria> searchQueryCriteria,
                         List<QueryCriteria> orderQueryCriteria);

    /**
     * Get revision numbers of gift certificate by id
     *
     * @param id the gift certificate id
     * @return list of gift certificate revisions
     */
    List<Number> getRevisionNumbers(int id);

    /**
     * Get gift certificate by id and revision number
     *
     * @param id             the gift certificate id
     * @param revisionNumber the gift certificate revision number
     * @return gift certificate
     */
    GiftCertificate findByRevisionNumber(int id, Number revisionNumber);

    /**
     * Get amount of revisions between date of gift certificate update
     * and date of order creation
     *
     * @param certificateId         the gift certificate id
     * @param certificateUpdateDate the date of gift certificate update
     * @param orderDate             the date of order creation
     * @return number of gift certificate revisions
     */
    int getRevisionsAmount(int certificateId,
                           LocalDateTime certificateUpdateDate,
                           LocalDateTime orderDate);
}