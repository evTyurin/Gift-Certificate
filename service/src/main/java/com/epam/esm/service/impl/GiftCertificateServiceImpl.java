package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.constants.ExceptionCode;
import com.epam.esm.service.util.Validation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class implements GiftCertificateService interface
 * This class includes methods that process requests from controller,
 * validate them, create query for finding by search criterion
 * and pass to dao class methods for injecting in queries to database.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final Validation validation;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository,
                                      TagRepository tagRepository,
                                      Validation validation) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.tagRepository = tagRepository;
        this.validation = validation;
    }

    @Transactional
    @Override
    public List<GiftCertificate> find(List<QueryCriteria> searchQueryCriteria,
                                      List<QueryCriteria> orderQueryCriteria,
                                      int page,
                                      int size) throws PageElementAmountException, PageNumberException {
        validation.pageElementAmountValidation(size);
        int giftCertificatesAmount = giftCertificateRepository.countByCriterion(searchQueryCriteria, orderQueryCriteria);
        validation.pageAmountValidation(giftCertificatesAmount, size, page);
        return giftCertificateRepository.findCertificatesByCriterion(searchQueryCriteria, orderQueryCriteria, page, size);
    }

    @Transactional
    @Override
    public GiftCertificate find(int giftCertificateId) throws NotFoundException {
        return giftCertificateRepository
                .findById(giftCertificateId)
                .orElseThrow(() -> new NotFoundException(giftCertificateId, ExceptionCode.NOT_FOUND_EXCEPTION));
    }

    @Transactional
    @Override
    public void delete(int giftCertificateId) throws NotFoundException {
        if (!giftCertificateRepository.findById(giftCertificateId).isPresent()) {
            throw new NotFoundException(giftCertificateId, ExceptionCode.NOT_FOUND_EXCEPTION);
        }
        giftCertificateRepository.delete(giftCertificateId);
    }

    @Transactional
    @Override
    public void update(int id, GiftCertificate updateGiftCertificate) throws EntityExistException, NotFoundException {
        GiftCertificate currantGiftCertificate = giftCertificateRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(id, ExceptionCode.NOT_FOUND_EXCEPTION));

        if ((!updateGiftCertificate.getName().equals(currantGiftCertificate.getName())
                && giftCertificateRepository.findByName(updateGiftCertificate.getName()).isPresent())) {
            throw new EntityExistException(id, ExceptionCode.NOT_FOUND_EXCEPTION);
        }

        List<Tag> tags = addIdToExistTags(updateGiftCertificate.getTags());
        updateGiftCertificate.setId(id);
        updateGiftCertificate.setTags(tags);
        updateGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        updateGiftCertificate.setCreateDate(currantGiftCertificate.getCreateDate());
        giftCertificateRepository.update(updateGiftCertificate);
    }

    @Transactional
    @Override
    public void partialUpdate(int giftCertificateId, GiftCertificate updateGiftCertificate) throws EntityExistException, NotFoundException {
        GiftCertificate currantGiftCertificate = giftCertificateRepository
                .findById(giftCertificateId)
                .orElseThrow(() -> new NotFoundException(giftCertificateId, ExceptionCode.NOT_FOUND_EXCEPTION));
        if (updateGiftCertificate.getName() != null && (!updateGiftCertificate.getName().equals(currantGiftCertificate.getName())
                && giftCertificateRepository.findByName(updateGiftCertificate.getName()).isPresent())) {
            throw new EntityExistException(giftCertificateId, ExceptionCode.NOT_FOUND_EXCEPTION);
        }
        if (updateGiftCertificate.getTags() != null && !updateGiftCertificate.getTags().isEmpty()) {
            List<Tag> tags = addIdToExistTags(updateGiftCertificate.getTags());
            currantGiftCertificate.setTags(tags);
        }
        Optional.ofNullable(updateGiftCertificate.getName()).ifPresent(currantGiftCertificate::setName);
        Optional.ofNullable(updateGiftCertificate.getDescription()).ifPresent(currantGiftCertificate::setDescription);
        Optional.ofNullable(updateGiftCertificate.getPrice()).ifPresent(currantGiftCertificate::setPrice);
        Optional.ofNullable(updateGiftCertificate.getDuration()).ifPresent(currantGiftCertificate::setDuration);
        currantGiftCertificate.setLastUpdateDate(LocalDateTime.now());
        giftCertificateRepository.update(currantGiftCertificate);
    }

    @Transactional
    @Override
    public void create(GiftCertificate giftCertificate) throws EntityExistException {
        if (giftCertificateRepository.findByName(giftCertificate.getName()).isPresent()) {
            throw new EntityExistException(giftCertificate.getId(), ExceptionCode.ENTITY_EXIST_EXCEPTION);
        }
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(giftCertificate.getCreateDate());
        List<Tag> tags = addIdToExistTags(giftCertificate.getTags());
        giftCertificate.setTags(tags);
        giftCertificateRepository.create(giftCertificate);
    }

    @Override
    @Transactional
    public int countByCriterion(List<QueryCriteria> searchQueryCriteria, List<QueryCriteria> orderQueryCriteria) {
        return giftCertificateRepository.countByCriterion(searchQueryCriteria, orderQueryCriteria);
    }

    private List<Tag> addIdToExistTags(List<Tag> tags) {
        return tags
                .stream()
                .map(tag -> tagRepository
                        .find(tag.getName())
                        .orElse(tag))
                .collect(Collectors.toList());
    }
}