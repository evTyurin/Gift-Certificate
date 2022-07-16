package com.epam.esm.service.impl;

import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.constants.ExceptionCode;
import com.epam.esm.service.util.SqlQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements GiftCertificateService interface
 * This class includes methods that process requests from controller,
 * validate them, create query for finding by search criterion
 * and pass to dao class methods for injecting in queries to database.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;
    private final SqlQueryBuilder sqlQueryBuilder;
    private final LocalDateTime localDateTime;

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO,
                                      TagDAO tagDAO,
                                      SqlQueryBuilder sqlQueryBuilder) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.sqlQueryBuilder = sqlQueryBuilder;
        this.localDateTime = LocalDateTime.now();
    }

    @Transactional
    @Override
    public List<GiftCertificate> find(List<QueryCriteria> searchQueryCriteria, List<QueryCriteria> orderQueryCriteria) {
        StringBuilder findCertificateIdByParamsQuery = new StringBuilder();
        findCertificateIdByParamsQuery.append(sqlQueryBuilder.createSearchPartOfQuery(searchQueryCriteria));
        findCertificateIdByParamsQuery.append(sqlQueryBuilder.createOrderedPartOfQuery(orderQueryCriteria));
        return giftCertificateDAO.findCertificatesByCriterion(findCertificateIdByParamsQuery.toString());
    }

    @Transactional
    @Override
    public GiftCertificate find(int giftCertificateId) throws NotFoundException {
        return giftCertificateDAO
                .find(giftCertificateId)
                .orElseThrow(() -> new NotFoundException(giftCertificateId, 40004));
    }

    @Transactional
    @Override
    public void delete(int giftCertificateId) throws NotFoundException {
        if(!giftCertificateDAO.find(giftCertificateId).isPresent()) {
            throw new NotFoundException(giftCertificateId, 40004);
        }
        giftCertificateDAO.delete(giftCertificateId);
    }

    @Transactional
    @Override
    public void update(int giftCertificateId, GiftCertificate updateGiftCertificate) throws EntityExistException, NotFoundException {
        GiftCertificate currantGiftCertificate = giftCertificateDAO
                .find(giftCertificateId)
                .orElseThrow(() -> new NotFoundException(giftCertificateId, 40004));

        if ((!updateGiftCertificate.getName().equals(currantGiftCertificate.getName())
                && giftCertificateDAO.find(updateGiftCertificate.getName()) != null)) {
            throw new EntityExistException(giftCertificateId, ExceptionCode.NOT_FOUND_EXCEPTION);
        }

        List<Tag> tags = addIdToExistTags(updateGiftCertificate.getTags());
        updateGiftCertificate.setTags(tags);
        updateGiftCertificate.setLastUpdateDate(localDateTime);
        updateGiftCertificate.setCreateDate(currantGiftCertificate.getCreateDate());
        giftCertificateDAO.update(updateGiftCertificate);
    }

    @Transactional
    @Override
    public void create(GiftCertificate giftCertificate) throws EntityExistException {
        if (giftCertificateDAO.find(giftCertificate.getName()) != null) {
            throw new EntityExistException(giftCertificate.getId(), ExceptionCode.ENTITY_EXIST_EXCEPTION);
        }
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        List<Tag> tags = addIdToExistTags(giftCertificate.getTags());
        giftCertificate.setTags(tags);
        giftCertificateDAO.create(giftCertificate);
    }

    private List<Tag> addIdToExistTags(List<Tag> tags) {
        return tags.stream().map(tag ->
                tagDAO.find(tag.getName()).orElse(tag)).collect(Collectors.toList());
    }
}