package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.constants.ExceptionCode;
import com.epam.esm.service.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class implements TagService interface
 * This class includes methods that process requests from controller,
 * validate them and pass to dao class methods for injecting in queries to database.
 */
@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final Validation validation;

    @Override
    public int findAmount() {
        return tagRepository.findAmount();
    }

    @Override
    public List<Tag> findMostWidelyUsedTag() {
        return tagRepository.findMostWidelyUsedTag();
    }

    @Override
    public List<Tag> findAll(int page, int size) throws PageElementAmountException, PageNumberException {
        validation.pageElementAmountValidation(size);
        int tagsAmount = tagRepository.findAmount();
        validation.pageAmountValidation(tagsAmount, size, page);
        return tagRepository.findAll(page, size);
    }

    @Override
    public Tag find(int tagId) throws NotFoundException {
        return tagRepository.find(tagId)
                .orElseThrow(()
                        -> new NotFoundException(tagId, ExceptionCode.NOT_FOUND_EXCEPTION));
    }

    @Transactional
    @Override
    public void create(Tag tag) throws EntityExistException {
        if (tagRepository.find(tag.getName()).isPresent()) {
            throw new EntityExistException(tag.getId(), ExceptionCode.ENTITY_EXIST_EXCEPTION);
        }
        tagRepository.create(tag.getName());
    }

    @Transactional
    @Override
    public void delete(int tagId) throws NotFoundException {
        if (!tagRepository.find(tagId).isPresent()) {
            throw new NotFoundException(tagId, ExceptionCode.ENTITY_EXIST_EXCEPTION);
        }
        tagRepository.delete(tagId);
    }
}
