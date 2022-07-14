package com.epam.esm.dao.impl;

import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateDAOUnitTest {
    @InjectMocks
    private GiftCertificateDAOImpl giftCertificateDAO;
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private GiftCertificateMapper giftCertificateMapper;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(jdbcTemplate,
                giftCertificateMapper);
    }

    @Test
    void delete_deleteCertificateById_deletedSuccessfully() {
        when(jdbcTemplate.update("DELETE FROM gift_certificate WHERE id = ?", 1)).thenReturn(1);

        assertTrue(giftCertificateDAO.delete(1));
    }

    @Test
    void find_findCertificateByName_returnedCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("sea");
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        doReturn(giftCertificates).when(jdbcTemplate).query("SELECT * FROM gift_certificate WHERE name = ?", giftCertificateMapper, "sea");

        assertEquals(giftCertificateDAO.find("sea"), giftCertificates.stream().findAny().orElse(null));
    }

    @Test
    void find_findCertificatesIdByCriteria_returnCertificates() {
        String query = "SELECT DISTINCT certificateId FROM (SELECT " +
                " gift_certificate.id AS certificateId," +
                " gift_certificate.name AS certificateName," +
                " gift_certificate.description AS description," +
                " gift_certificate.price AS price," +
                " gift_certificate.duretion AS duration," +
                " gift_certificate.create_date AS createDate," +
                " gift_certificate.last_update_date AS lastUpdateDate," +
                " tag.name AS tagName," +
                " FROM gift_certificate JOIN" +
                " gift_certificate_has_tag ON" +
                " gift_certificate_has_tag.gift_certificate_id = gift_certificate.id" +
                " JOIN tag ON tag.id = gift_certificate_has_tag.tag_id) AS t WHERE tagName LIKE 'sea'";
        List<Integer> giftCertificatesId = new ArrayList<>();
        giftCertificatesId.add(1);
        giftCertificatesId.add(4);
        when(jdbcTemplate.queryForList(query, Integer.class)).thenReturn(giftCertificatesId);

        assertEquals(giftCertificateDAO.findCertificatesIdByParams(query), giftCertificatesId);
    }

    @Test
    void find_findCertificateById_returnCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("sea");
        giftCertificate.setId(1);
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(giftCertificate);
        doReturn(giftCertificates).when(jdbcTemplate).query("SELECT * FROM gift_certificate WHERE id = ?", giftCertificateMapper, 1);

        assertEquals(giftCertificateDAO.find(1), giftCertificates.stream().findAny().orElse(null));
    }

    @Test
    void findId_findCertificateIdByName_returnId() {
        when(jdbcTemplate.queryForObject("SELECT id FROM gift_certificate WHERE name = ?", Integer.class, "sea")).thenReturn(1);

        assertEquals(1, giftCertificateDAO.findId("sea"));
    }
}