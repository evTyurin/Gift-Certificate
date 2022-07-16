package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.esm.builder.GiftCertificateBuilder;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftCertificateControllerUnitTest {
    @InjectMocks
    private GiftCertificateController giftCertificateController;
    @Mock
    private GiftCertificateService giftCertificateService;
    @Mock
    private GiftCertificateBuilder giftCertificateBuilder;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(giftCertificateBuilder,
                giftCertificateService);
    }

    @Test
    void find_findById_returnCertificate() throws NotFoundException {
        GiftCertificate giftCertificate = new GiftCertificate();
        List<Tag> tagList = new ArrayList<>();
        Tag tag = new Tag();
        tag.setName("aaa");
        tagList.add(tag);
        giftCertificate.setTags(tagList);
        giftCertificate.setId(1);
        giftCertificate.setName("sea");
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        List<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto = new TagDto();
        tagDto.setName("aaa");
        tagDtos.add(tagDto);
        giftCertificateDto.setTags(tagDtos);
        giftCertificateDto.setId(1);
        giftCertificateDto.setName("sea");

        doReturn(giftCertificate).when(giftCertificateService).find(1);
        doReturn(giftCertificateDto).when(giftCertificateBuilder).build(giftCertificate);

        assertEquals(giftCertificateController.search(1), giftCertificateDto);

        verify(giftCertificateBuilder).build(giftCertificate);
        verify(giftCertificateService).find(1);
    }

    @Test
    void delete_deleteById_deletedSuccessfully() throws NotFoundException {
        giftCertificateController.deleteById(1);

        verify(giftCertificateService).delete(1);
    }
}
