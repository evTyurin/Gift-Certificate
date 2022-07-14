package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.ArrayList;
import java.util.List;

class TagDAOImplIntegrationTest {

    private TagDAO tagDAO;
    private GiftCertificateDAO giftCertificateDAO;
    private List<Tag> testTags;

    @BeforeEach
    public void initTestTags() {
        initTagDaoImpl(initBDConnection());
        createTestTags();
    }

    @Test
    void findAllTest() {
        List<Tag> allTags = tagDAO.findAll();
        Assertions.assertEquals(allTags, testTags);
    }

    @Test
    void find_findTagsByCertificateId_returnedTags() {
        List<Tag> allTagsByCertificateId = tagDAO.findAll(1);
        Assertions.assertNotEquals(allTagsByCertificateId, testTags);
    }

    @Test
    void create_createTag_createdSuccessfully() {
        Tag testTag = new Tag();
        testTag.setName("tag4");
        tagDAO.create("tag4");
        Tag searchTag = tagDAO.findByName("tag4");
        Assertions.assertEquals(testTag, searchTag);
    }

    @Test
    void findByIdTest() {
        Tag testTag = tagDAO.findById(1);
        Assertions.assertEquals(1, testTag.getId());
    }

    @Test
    void find_findByName_returnedTag() {
        Tag testTag = tagDAO.findByName("tag1");
        Assertions.assertEquals("tag1", testTag.getName());
    }

    @Test
    void delete_deleteById_deletedSuccessfully() {
        Tag testTag = new Tag();
        testTag.setName("tag5");

        List<Tag> listBefore = tagDAO.findAll();

        tagDAO.create("tag5");
        List<Tag> listAfterCreation = tagDAO.findAll();
        Assertions.assertNotEquals(listAfterCreation, listBefore);
        tagDAO.deleteById(tagDAO.findId("tag5"));

        List<Tag> listAfterDeletion = tagDAO.findAll();
        Assertions.assertEquals(listBefore, listAfterDeletion);
    }

    @Test
    void find_findIdByName_returnedId() {
        int id = tagDAO.findId("tag1");
        Assertions.assertEquals(1, id);
    }

    @Test
    void find_findTagsId_returnedListId() {
        List<Integer> idList = tagDAO.findTagsId(1);
        List<Integer> preparedIdList = new ArrayList<>();
        preparedIdList.add(1);
        preparedIdList.add(2);
        Assertions.assertEquals(idList, preparedIdList);
    }

    @Test
    void addTagCertificateConnection_addTagCertificateConnection_addedSuccessfully() {
        tagDAO.addTagCertificateConnection(1, 3);
        List<Integer> preparedIdList = new ArrayList<>();
        preparedIdList.add(1);
        preparedIdList.add(2);
        preparedIdList.add(3);
        List<Integer> idList = tagDAO.findTagsId(1);
        Assertions.assertEquals(idList, preparedIdList);
    }

    @Test
    void deleteTagCertificateConnection_deleteTagCertificateConnection_deletedSuccessfully() {
        tagDAO.deleteTagCertificateConnection(1, 3);
        List<Integer> preparedIdList = new ArrayList<>();
        preparedIdList.add(1);
        preparedIdList.add(2);
        List<Integer> idList = tagDAO.findTagsId(1);
        Assertions.assertEquals(idList, preparedIdList);
    }

    @Test
    void deleteTagCertificateConnectionByTagId_deleteAllConnectionsByTagId_deletedSuccessfully() {
        tagDAO.deleteTagCertificateConnection(3);
        List<Integer> preparedIdList = new ArrayList<>();
        preparedIdList.add(1);
        preparedIdList.add(2);
        List<Integer> idList = tagDAO.findTagsId(1);
        Assertions.assertEquals(idList, preparedIdList);
    }

    @Test
    void ifExistTagCertificateConnection_checkIfConnectionExist() {
        Tag tag = giftCertificateDAO.find(1).getTags().get(1);
        Assertions.assertTrue(tagDAO.ifExistTagCertificateConnection(1, tag.getId()));
    }

    private DriverManagerDataSource initBDConnection() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/gift-certificate-test");
        dataSource.setUsername("root");
        dataSource.setPassword("ETmysql21@");
        return dataSource;
    }

    private void initTagDaoImpl(DriverManagerDataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        TagMapper tagMapper = new TagMapper();
        GiftCertificateMapper giftCertificateMapper = new GiftCertificateMapper();
        tagDAO = new TagDAOImpl(jdbcTemplate, tagMapper);
        giftCertificateDAO = new GiftCertificateDAOImpl(jdbcTemplate, giftCertificateMapper);
    }

    private void createTestTags() {
        testTags = new ArrayList<>();
        Tag testTag1 = new Tag(1, "tag1");
        Tag testTag2 = new Tag(2, "tag2");
        Tag testTag3 = new Tag(3, "tag3");
        testTags.add(testTag1);
        testTags.add(testTag2);
        testTags.add(testTag3);
    }
}