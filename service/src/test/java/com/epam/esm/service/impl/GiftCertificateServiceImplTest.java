package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.constants.ExceptionCode;
import com.epam.esm.service.util.Validation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private Validation validation;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(giftCertificateRepository,
                tagRepository,
                validation);
    }

    @Test
    void find_findGiftCertificateByCorrectId_returnGiftCertificate() throws NotFoundException {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);

        when(giftCertificateRepository.find(1)).thenReturn(Optional.of(giftCertificate));

        GiftCertificate actualGiftCertificate = giftCertificateRepository.find(1).orElseThrow(() -> new NotFoundException(1, ExceptionCode.NOT_FOUND_EXCEPTION));
        assertEquals(giftCertificate, actualGiftCertificate);
    }

    @Test
    void find_findGiftCertificateByIncorrectId_throwException() {
        when(giftCertificateRepository.find(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> giftCertificateService.find(1));
    }

    @Test
    void delete_deleteGiftCertificateByCorrectId_deleteGiftCertificate() throws NotFoundException {
        GiftCertificate expectedGiftCertificate = new GiftCertificate();
        expectedGiftCertificate.setId(1);

        when(giftCertificateRepository.find(1)).thenReturn(Optional.of(expectedGiftCertificate));

        giftCertificateService.delete(1);

        verify(giftCertificateRepository).delete(1);
    }

    @Test
    void delete_deleteGiftCertificateByIncorrectId_throwException() {
        GiftCertificate expectedGiftCertificate = new GiftCertificate();
        expectedGiftCertificate.setId(1);

        when(giftCertificateRepository.find(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> giftCertificateService.delete(1));
    }

    @Test
    void countByCriterion_countGiftCertificatesByCriterion_returnNumberOfGiftCertificates() {
        List<QueryCriteria> searchQueryCriteria = new ArrayList<>();
        List<QueryCriteria> orderQueryCriteria = new ArrayList<>();

        when(giftCertificateRepository.countByCriterion(searchQueryCriteria, orderQueryCriteria)).thenReturn(100);

        int amountOfGiftCertificatesByCriterion = giftCertificateService.countByCriterion(searchQueryCriteria, orderQueryCriteria);

        assertEquals(100, amountOfGiftCertificatesByCriterion);
    }

    @Test
    void find_findGiftCertificatesByCriterion_returnGiftCertificates() throws PageElementAmountException, PageNumberException {
        doNothing().when(validation).pageAmountValidation(anyInt(), anyInt(), anyInt());
        doNothing().when(validation).pageElementAmountValidation(anyInt());

        List<QueryCriteria> searchQueryCriteria = new ArrayList<>();
        List<QueryCriteria> orderQueryCriteria = new ArrayList<>();

        when(giftCertificateRepository.countByCriterion(searchQueryCriteria, orderQueryCriteria)).thenReturn(1);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        List<GiftCertificate> expectedGiftCertificates = new ArrayList<>();
        expectedGiftCertificates.add(giftCertificate);

        when(giftCertificateRepository.findCertificatesByCriterion(searchQueryCriteria,orderQueryCriteria, 1,2)).thenReturn(expectedGiftCertificates);

        List<GiftCertificate> actualGiftCertificates = giftCertificateService.find(searchQueryCriteria,orderQueryCriteria, 1,2);

        assertEquals(actualGiftCertificates, expectedGiftCertificates);
    }
}
