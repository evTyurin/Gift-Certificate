package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.util.SqlQueryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceUnitTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private SqlQueryBuilder sqlQueryBuilder;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(giftCertificateDAO,
                tagDAO, sqlQueryBuilder);
    }

    @Test
    void find_findByGiftCertificateName_returnGiftCertificate() throws NotFoundException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setName("sea");
        giftCertificate.setDescription("sea story");
        giftCertificate.setPrice(250);
        giftCertificate.setDuration(4);
        giftCertificate.setCreateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        giftCertificate.setLastUpdateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));

        GiftCertificate giftCertificateExpected = new GiftCertificate();
        giftCertificateExpected.setId(1);
        giftCertificateExpected.setName("sea");
        giftCertificateExpected.setDescription("sea story");
        giftCertificateExpected.setPrice(250);
        giftCertificateExpected.setDuration(4);
        giftCertificateExpected.setCreateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        giftCertificateExpected.setLastUpdateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        giftCertificateExpected.setTags(new ArrayList<>(Arrays
                .asList(new Tag(4, "forest"),
                        new Tag(206, "fofofo"))));

        when(giftCertificateDAO.find(1)).thenReturn(giftCertificate);
        when(tagDAO.findById(anyInt())).thenReturn(new Tag());
        when(tagDAO.findAll(1)).thenReturn(new ArrayList<>(Arrays
                .asList(new Tag(4, "forest"),
                        new Tag(206, "fofofo"))));
        assertEquals(giftCertificateService.find(1), giftCertificateExpected);

        verify(giftCertificateDAO).find(1);
        verify(tagDAO).findById(anyInt());
        verify(tagDAO).findAll(1);
    }

    @Test
    void find_findCertificatesByParams_returnGiftCertificates() {
        String sqlQuery = "SELECT" +
                " gift_certificate.id AS certificateId," +
                " gift_certificate.name AS certificateName," +
                " gift_certificate.description AS description," +
                " gift_certificate.price AS price," +
                " gift_certificate.duretion AS duration," +
                " gift_certificate.create_date AS createDate," +
                " gift_certificate.last_update_date AS lastUpdateDate," +
                " tag.name AS tagName" +
                " FROM gift_certificate JOIN" +
                " gift_certificate_has_tag ON" +
                " gift_certificate_has_tag.gift_certificate_id = gift_certificate.id" +
                " JOIN tag ON tag.id = gift_certificate_has_tag.tag_id" +
                " WHERE tagName LIKE 'water'";

        GiftCertificate firstGiftCertificateExpected = new GiftCertificate();
        firstGiftCertificateExpected.setId(1);
        firstGiftCertificateExpected.setName("sea");
        firstGiftCertificateExpected.setDescription("sea story");
        firstGiftCertificateExpected.setPrice(250);
        firstGiftCertificateExpected.setDuration(4);
        firstGiftCertificateExpected.setCreateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        firstGiftCertificateExpected.setLastUpdateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        firstGiftCertificateExpected.setTags(new ArrayList<>(Arrays
                .asList(new Tag(1, "sea"),
                        new Tag(2, "water"))));

        GiftCertificate secondGiftCertificateExpected = new GiftCertificate();
        secondGiftCertificateExpected.setId(2);
        secondGiftCertificateExpected.setName("baltic sea");
        secondGiftCertificateExpected.setDescription("sea story");
        secondGiftCertificateExpected.setPrice(200);
        secondGiftCertificateExpected.setDuration(7);
        secondGiftCertificateExpected.setCreateDate(LocalDateTime.of(2021, 5, 6, 12, 12, 12));
        secondGiftCertificateExpected.setLastUpdateDate(LocalDateTime.of(2021, 5, 6, 12, 12, 12));
        secondGiftCertificateExpected.setTags(new ArrayList<>(Arrays
                .asList(new Tag(11, "baltic sea"),
                        new Tag(2, "water"))));

        GiftCertificate firstGiftCertificateExpectedWithoutTags = new GiftCertificate();
        firstGiftCertificateExpectedWithoutTags.setId(1);
        firstGiftCertificateExpectedWithoutTags.setName("sea");
        firstGiftCertificateExpectedWithoutTags.setDescription("sea story");
        firstGiftCertificateExpectedWithoutTags.setPrice(250);
        firstGiftCertificateExpectedWithoutTags.setDuration(4);
        firstGiftCertificateExpectedWithoutTags.setCreateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        firstGiftCertificateExpectedWithoutTags.setLastUpdateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));

        GiftCertificate secondGiftCertificateExpectedWithoutTags = new GiftCertificate();
        secondGiftCertificateExpectedWithoutTags.setId(2);
        secondGiftCertificateExpectedWithoutTags.setName("baltic sea");
        secondGiftCertificateExpectedWithoutTags.setDescription("sea story");
        secondGiftCertificateExpectedWithoutTags.setPrice(200);
        secondGiftCertificateExpectedWithoutTags.setDuration(7);
        secondGiftCertificateExpectedWithoutTags.setCreateDate(LocalDateTime.of(2021, 5, 6, 12, 12, 12));
        secondGiftCertificateExpectedWithoutTags.setLastUpdateDate(LocalDateTime.of(2021, 5, 6, 12, 12, 12));

        QueryCriteria queryCriteria = new QueryCriteria("tagName", "water");
        List<QueryCriteria> searchQueryCriteria = new ArrayList<>();
        searchQueryCriteria.add(queryCriteria);
        List<QueryCriteria> sortQueryCriteria = new ArrayList<>();

        when(sqlQueryBuilder.createSearchPartOfQuery(searchQueryCriteria)).thenReturn(sqlQuery);
        when(sqlQueryBuilder.createOrderedPartOfQuery(sortQueryCriteria)).thenReturn("");
        List<Integer> certificatesId = new ArrayList<>();
        certificatesId.add(1);
        certificatesId.add(2);
        when(giftCertificateDAO
                .findCertificatesIdByParams(sqlQuery)).thenReturn(certificatesId);
        when(giftCertificateDAO.find(1)).thenReturn(firstGiftCertificateExpectedWithoutTags);
        when(giftCertificateDAO.find(certificatesId.get(1))).thenReturn(secondGiftCertificateExpectedWithoutTags);
        when(tagDAO.findAll(firstGiftCertificateExpectedWithoutTags.getId())).thenReturn(new ArrayList<>(Arrays
                .asList(new Tag(1, "sea"),
                        new Tag(2, "water"))));
        when(tagDAO.findAll(secondGiftCertificateExpected.getId())).thenReturn(new ArrayList<>(Arrays
                .asList(new Tag(11, "baltic sea"),
                        new Tag(2, "water"))));

        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(firstGiftCertificateExpected);
        giftCertificates.add(secondGiftCertificateExpected);

        assertEquals(giftCertificateService.find(searchQueryCriteria, sortQueryCriteria), giftCertificates);

        verify(sqlQueryBuilder).createSearchPartOfQuery(searchQueryCriteria);
        verify(sqlQueryBuilder).createOrderedPartOfQuery(sortQueryCriteria);
        verify(giftCertificateDAO).findCertificatesIdByParams(sqlQuery);
        verify(giftCertificateDAO).find(1);
        verify(giftCertificateDAO).find(certificatesId.get(1));
        verify(tagDAO).findAll(firstGiftCertificateExpectedWithoutTags.getId());
        verify(tagDAO).findAll(secondGiftCertificateExpected.getId());
    }

    @Test
    void delete_deleteCertificateById_deletedSuccessfully() throws NotFoundException {
        when(giftCertificateDAO.find(1)).thenReturn(new GiftCertificate());
        List<Integer> tagsId = new ArrayList<>();
        tagsId.add(1);
        tagsId.add(2);
        when(tagDAO.findTagsId(1)).thenReturn(tagsId);
        when(tagDAO.deleteTagCertificateConnection(1, 1)).thenReturn(true);
        when(tagDAO.deleteTagCertificateConnection(1, 2)).thenReturn(true);
        when(giftCertificateDAO.delete(1)).thenReturn(true);

        giftCertificateService.delete(1);

        verify(giftCertificateDAO).find(1);
        verify(tagDAO).findTagsId(1);
        verify(tagDAO).deleteTagCertificateConnection(1, 1);
        verify(tagDAO).deleteTagCertificateConnection(1, 2);
        verify(giftCertificateDAO).delete(1);
    }

    @Test
    void update_updateExistingCertificate_updatedSuccessfully() {
        GiftCertificate firstGiftCertificateExpected = new GiftCertificate();
        firstGiftCertificateExpected.setId(1);
        firstGiftCertificateExpected.setName("sea");
        firstGiftCertificateExpected.setDescription("sea story");
        firstGiftCertificateExpected.setPrice(250);
        firstGiftCertificateExpected.setDuration(4);
        firstGiftCertificateExpected.setCreateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        firstGiftCertificateExpected.setLastUpdateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        firstGiftCertificateExpected.setTags(new ArrayList<>(Collections
                .singletonList(new Tag(1, "sea"))));

        when(giftCertificateDAO.find(1)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> giftCertificateService.update(1, firstGiftCertificateExpected));

        verify(giftCertificateDAO).find(1);
    }

    @Test
    void create_createNewCertificate_createdSuccessfully() throws EntityExistException {
        GiftCertificate firstGiftCertificateExpected = new GiftCertificate();
        firstGiftCertificateExpected.setId(1);
        firstGiftCertificateExpected.setName("sea");
        firstGiftCertificateExpected.setDescription("sea story");
        firstGiftCertificateExpected.setPrice(250);
        firstGiftCertificateExpected.setDuration(4);
        firstGiftCertificateExpected.setCreateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        firstGiftCertificateExpected.setLastUpdateDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12));
        firstGiftCertificateExpected.setTags(new ArrayList<>(Arrays
                .asList(new Tag(1, "sea"),
                        new Tag(2, "water"))));

        when(giftCertificateDAO.find(firstGiftCertificateExpected.getName())).thenReturn(null);
        when(giftCertificateDAO.create(firstGiftCertificateExpected)).thenReturn(true);
        when(tagDAO.findByName("sea")).thenReturn(new Tag(1, "sea"));
        when(tagDAO.findByName("water")).thenReturn(new Tag(2, "water"));
        when(giftCertificateDAO.findId("sea")).thenReturn(1);
        when(tagDAO.findId("sea")).thenReturn(1);
        when(tagDAO.findId("water")).thenReturn(2);
        when(tagDAO.addTagCertificateConnection(1, 1)).thenReturn(true);
        when(tagDAO.addTagCertificateConnection(1, 2)).thenReturn(true);

        giftCertificateService.create(firstGiftCertificateExpected);

        verify(giftCertificateDAO).find(firstGiftCertificateExpected.getName());
        verify(giftCertificateDAO).create(firstGiftCertificateExpected);
        verify(tagDAO).findByName("sea");
        verify(tagDAO).findByName("water");
        verify(giftCertificateDAO, times(2)).findId("sea");
        verify(tagDAO).findId("sea");
        verify(tagDAO).findId("water");
        verify(tagDAO).addTagCertificateConnection(1, 1);
        verify(tagDAO).addTagCertificateConnection(1, 2);
    }
}