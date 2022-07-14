package com.epam.esm.dao.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.mapper.TagMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    public TagDAOImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<Tag> findAll() {
        final String READ_ALL_TAGS = "SELECT * FROM tag";
        return jdbcTemplate.query(READ_ALL_TAGS, tagMapper);
    }

    @Override
    public List<Tag> findAll(int giftCertificateId) {

        final String READ_ALL_TAGS_BY_CERTIFICATE_ID = "SELECT * FROM tag" +
                " WHERE id IN (SELECT tag_id FROM gift_certificate_has_tag" +
                " WHERE gift_certificate_id=?)";
        return jdbcTemplate.query(READ_ALL_TAGS_BY_CERTIFICATE_ID, tagMapper, giftCertificateId);
    }

    @Override
    public Tag findById(int id) {
        final String READ_TAG_BY_ID = "SELECT * FROM tag WHERE id = ?";
        return jdbcTemplate.query(READ_TAG_BY_ID, tagMapper, id)
                .stream().findAny().orElse(null);
    }

    @Override
    public Tag findByName(String name) {
        final String READ_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
        return jdbcTemplate.query(READ_TAG_BY_NAME, tagMapper, name)
                .stream().findAny().orElse(null);
    }

    @Override
    public int findId(String tagName) {
        final String READ_TAG_ID_BY_NAME = "SELECT id" +
                " FROM tag WHERE name = ?";
        return jdbcTemplate.queryForObject(READ_TAG_ID_BY_NAME,
                Integer.class, tagName);
    }

    @Override
    public void create(String name) {
        final String CREATE_TAG = "INSERT INTO" +
                " tag (name) VALUES (?)";
        jdbcTemplate.update(CREATE_TAG, name);
    }

    @Override
    public void create(int id, String name) {
        final String CREATE_TAG = "INSERT INTO" +
                " tag (id, name) VALUES (?,?)";
        jdbcTemplate.update(CREATE_TAG, id, name);
    }

    @Override
    public boolean addTagCertificateConnection(int certificateId, int tagId) {
        final String ADD_TAG_ID_TO_CERTIFICATE = "INSERT INTO" +
                " gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (?,?)";
        return jdbcTemplate.update(ADD_TAG_ID_TO_CERTIFICATE,
                certificateId, tagId) >= 1;
    }

    @Override
    public boolean deleteTagCertificateConnection(int certificateId, int tagId) {
        final String DELETE_TAG_CERTIFICATE_CONNECTION = "DELETE" +
                " FROM gift_certificate_has_tag" +
                " WHERE gift_certificate_id=? AND tag_id=?";
        return jdbcTemplate.update(DELETE_TAG_CERTIFICATE_CONNECTION, certificateId, tagId) >= 1;
    }

    @Override
    public boolean deleteTagCertificateConnection(int tagId) {
        final String DELETE_TAG_CONNECTION = "DELETE" +
                " FROM gift_certificate_has_tag WHERE tag_id=?";
        return jdbcTemplate.update(DELETE_TAG_CONNECTION, tagId) >= 1;
    }

    @Override
    public boolean ifExistTagCertificateConnection(int certificateId, int tagId) {
        final String SEARCH_TAG_CERTIFICATE_CONNECTION = "SELECT" +
                " EXISTS(SELECT 1 FROM gift_certificate_has_tag" +
                " WHERE gift_certificate_id=? AND tag_id=?)";
        return jdbcTemplate.queryForObject(SEARCH_TAG_CERTIFICATE_CONNECTION,
                Boolean.class, certificateId, tagId);
    }

    @Override
    public List<Integer> findTagsId(int certificateId) {
        final String READ_TAG_ID_BY_CERTIFICATE_ID = "SELECT" +
                " tag_id FROM gift_certificate_has_tag" +
                " WHERE gift_certificate_id=?";
        return jdbcTemplate.queryForList(READ_TAG_ID_BY_CERTIFICATE_ID,
                Integer.class, certificateId);
    }

    @Override
    public boolean deleteById(int id) {
        final String DELETE_TAG_BY_ID = "DELETE FROM tag WHERE id = ?";
        return jdbcTemplate.update(DELETE_TAG_BY_ID, id) >= 1;
    }
}