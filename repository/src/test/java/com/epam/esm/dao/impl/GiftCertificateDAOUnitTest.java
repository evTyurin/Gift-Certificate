package com.epam.esm.dao.impl;

import com.epam.esm.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateDAOUnitTest {
    @InjectMocks
    private GiftCertificateDAOImpl giftCertificateDAO;
    @Mock
    private EntityManager entityManager;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(entityManager);
    }

    @Test
    void create_createCertificateById_createSuccessfully() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("sea");
        giftCertificate.setId(1);

        giftCertificateDAO.create(giftCertificate);

        verify(entityManager).persist(giftCertificate);
    }

    @Test
    void delete_deleteCertificateById_deletedSuccessfully() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("sea");
        giftCertificate.setId(1);

        when(entityManager.find(GiftCertificate.class, 1)).thenReturn(giftCertificate);
        giftCertificateDAO.delete(1);

        verify(entityManager).remove(giftCertificate);
    }

    @Test
    void update_updateCertificateById_updatedSuccessfully() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("sea");
        giftCertificate.setId(1);

        giftCertificateDAO.create(giftCertificate);

        verify(entityManager).persist(giftCertificate);
    }

    @Test
    void find_findCertificateById_findCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName("sea");
        giftCertificate.setId(1);

        when(entityManager.find(GiftCertificate.class, 1)).thenReturn(giftCertificate);
        assertEquals(giftCertificateDAO.find(1).get(), giftCertificate);

        verify(entityManager).find(GiftCertificate.class, 1);
    }
}