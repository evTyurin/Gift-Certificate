package com.epam.esm.service.impl;

import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.entity.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.service.constants.ExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This class implements TagService interface
 * This class includes methods that process requests from controller,
 * validate them and pass to dao class methods for injecting in queries to database.
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagDAO tagDAO;

    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public List<Tag> findAll() {
        return tagDAO.findAll();
    }

    @Override
    public Tag find(int tagId) throws NotFoundException {
        Tag tag = tagDAO.findById(tagId);
        if (tag == null) {
            throw new NotFoundException(tagId, ExceptionCode.NOT_FOUND_EXCEPTION);
        }
        return tag;
    }

    @Transactional
    @Override
    public void create(Tag tag) throws EntityExistException {
        if (tagDAO.findByName(tag.getName()) != null) {
            throw new EntityExistException(tag.getId(), ExceptionCode.ENTITY_EXIST_EXCEPTION);
        }
        tagDAO.create(tag.getName());
    }

    @Transactional
    @Override
    public void delete(int tagId) throws NotFoundException {
        if (tagDAO.findById(tagId) == null) {
            throw new NotFoundException(tagId, ExceptionCode.ENTITY_EXIST_EXCEPTION);
        }
        tagDAO.deleteTagCertificateConnection(tagId);
        tagDAO.deleteById(tagId);
    }
}