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

import java.util.ArrayList;
import java.util.List;

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

    public GiftCertificateServiceImpl(GiftCertificateDAO giftCertificateDAO,
                                      TagDAO tagDAO,
                                      SqlQueryBuilder sqlQueryBuilder) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
        this.sqlQueryBuilder = sqlQueryBuilder;
    }

    @Transactional
    @Override
    public List<GiftCertificate> find(List<QueryCriteria> searchQueryCriteria, List<QueryCriteria> orderQueryCriteria) {
        StringBuilder findCertificateIdByParamsQuery = new StringBuilder();
        findCertificateIdByParamsQuery.append(sqlQueryBuilder.createSearchPartOfQuery(searchQueryCriteria));
        findCertificateIdByParamsQuery.append(sqlQueryBuilder.createOrderedPartOfQuery(orderQueryCriteria));
        List<Integer> certificatesId = giftCertificateDAO
                .findCertificatesIdByParams(findCertificateIdByParamsQuery.toString());
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        certificatesId.forEach(certificateId -> {
            GiftCertificate giftCertificate = giftCertificateDAO.find(certificateId);
            giftCertificate.setTags(tagDAO.findAll(certificateId));
            giftCertificates.add(giftCertificate);
        });
        return giftCertificates;
    }

    @Transactional
    @Override
    public GiftCertificate find(int giftCertificateId) throws NotFoundException {
        if (tagDAO.findById(giftCertificateId) == null) {
            throw new NotFoundException(giftCertificateId,
                    "40004");
        }
        GiftCertificate giftCertificate = giftCertificateDAO.find(giftCertificateId);
        giftCertificate.setTags(tagDAO.findAll(giftCertificateId));
        return giftCertificate;
    }

    @Transactional
    @Override
    public void delete(int giftCertificateId) throws NotFoundException {
        if (giftCertificateDAO.find(giftCertificateId) == null) {
            throw new NotFoundException(giftCertificateId, ExceptionCode.NOT_FOUND_EXCEPTION);
        }
        List<Integer> tagsId = tagDAO.findTagsId(giftCertificateId);
        tagsId.forEach(tagId -> tagDAO
                .deleteTagCertificateConnection(giftCertificateId, tagId));
        giftCertificateDAO.delete(giftCertificateId);
    }

    @Transactional
    @Override
    public void update(int giftCertificateId, GiftCertificate giftCertificate) throws EntityExistException, NotFoundException {
        if ((giftCertificateDAO.find(giftCertificateId) == null)) {
            throw new NotFoundException(giftCertificateId, ExceptionCode.NOT_FOUND_EXCEPTION);
        }

        if ((!giftCertificate.getName().equals(giftCertificateDAO.find(giftCertificateId).getName())
                && giftCertificateDAO.find(giftCertificate.getName()) != null)) {
            throw new EntityExistException(giftCertificateId, ExceptionCode.NOT_FOUND_EXCEPTION);
        }

        for (Tag newTag : giftCertificate.getTags()) {
            if (tagDAO.findByName(newTag.getName()) == null) {
                tagDAO.create(newTag.getName());
                int tagId = tagDAO.findId(newTag.getName());
                tagDAO.addTagCertificateConnection(giftCertificateId, tagId);
                continue;
            }
            if (!tagDAO.ifExistTagCertificateConnection(giftCertificateId, tagDAO.findId(newTag.getName()))) {
                tagDAO.addTagCertificateConnection(giftCertificateId, tagDAO.findId(newTag.getName()));
            }
        }

        List<Tag> oldTags = tagDAO.findAll(giftCertificateId);
        oldTags
                .stream()
                .filter(oldTag -> !giftCertificate.getTags().contains(oldTag))
                .forEach(oldTag -> tagDAO.deleteTagCertificateConnection(giftCertificateId, oldTag.getId()));
        giftCertificateDAO.update(giftCertificateId, giftCertificate);
    }

    @Transactional
    @Override
    public void create(GiftCertificate giftCertificate) throws EntityExistException {
        if (giftCertificateDAO.find(giftCertificate.getName()) != null) {
            throw new EntityExistException(giftCertificate.getId(), ExceptionCode.ENTITY_EXIST_EXCEPTION);
        }
        giftCertificateDAO.create(giftCertificate);
        for (Tag tag : giftCertificate.getTags()) {
            if (tagDAO.findByName(tag.getName()) == null) {
                tagDAO.create(tag.getName());
            }
            int certificateId = giftCertificateDAO.findId(giftCertificate.getName());
            int tagId = tagDAO.findId(tag.getName());
            tagDAO.addTagCertificateConnection(certificateId, tagId);
        }
    }
}