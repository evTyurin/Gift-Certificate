package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;

import java.util.List;

/**
 * The GiftCertificate is interface that contains CRUD methods to work with GiftCertificates
 */
public interface GiftCertificateService {

    /**
     * Delete GiftCertificate by id
     *
     * @param giftCertificateId the gift certificate id
     * @throws NotFoundException indicates that gift certificate with this id not exist
     */
    void delete(int giftCertificateId) throws NotFoundException;

    /**
     * Create new GiftCertificate
     *
     * @param giftCertificate the gift certificate entity
     * @throws EntityExistException indicates that tag with this name already exist
     */
    void create(GiftCertificate giftCertificate) throws EntityExistException;

    /**
     * Update exist GiftCertificate
     *
     * @param id              the gift certificate id
     * @param giftCertificate the gift certificate entity
     * @throws EntityExistException indicates that tag with name for update already exist
     * @throws NotFoundException    indicates that gift certificate with this id not exist
     */
    void update(int id,
                GiftCertificate giftCertificate) throws EntityExistException, NotFoundException;

    /**
     * Partial update exist GiftCertificate
     *
     * @param id              the gift certificate id
     * @param giftCertificate the gift certificate entity
     * @throws EntityExistException indicates that tag with name for update already exist
     * @throws NotFoundException    indicates that gift certificate with this id not exist
     */
    void partialUpdate(int id,
                       GiftCertificate giftCertificate) throws EntityExistException,
            NotFoundException;

    /**
     * Find GiftCertificate by id
     *
     * @param certificateId the gift certificate id
     * @return the gift certificate entity
     * @throws NotFoundException indicates that gift certificate with this id not exist
     */
    GiftCertificate find(int certificateId) throws NotFoundException;

    /**
     * Find GiftCertificates by criteria
     *
     * @param searchQueryCriteria criterion used for search
     * @param orderQueryCriteria  criterion used for search
     * @param pageNumber          the number of page being viewed
     * @param pageElementAmount   amount of elements per page
     * @return list of gift certificates found and ordered by criterion
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     */
    List<GiftCertificate> find(List<QueryCriteria> searchQueryCriteria,
                               List<QueryCriteria> orderQueryCriteria,
                               int pageNumber,
                               int pageElementAmount) throws
            PageElementAmountException,
            PageNumberException;

    /**
     * @param searchQueryCriteria criterion used for search
     * @param orderQueryCriteria  criterion used for sort
     * @return amount of instances that match search parameters
     */
    int countByCriterion(List<QueryCriteria> searchQueryCriteria,
                         List<QueryCriteria> orderQueryCriteria);
}