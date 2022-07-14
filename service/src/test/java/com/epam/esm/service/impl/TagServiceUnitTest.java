package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceUnitTest {

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDAO tagDAO;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(tagDAO);
    }

    @Test
    void find_findTagById_returnedTag() throws NotFoundException {
        when(tagDAO.findById(1)).thenReturn(new Tag(4, "sea"));
        Tag tagReturned = tagService.find(1);

        assertEquals(tagReturned, new Tag(4, "sea"));

        verify(tagDAO).findById(1);
    }

    @Test
    void find_findAllTags_returnedTags() {
        when(tagDAO.findAll()).thenReturn(new ArrayList<>(Arrays
                .asList(new Tag(1, "sea"),
                        new Tag(2, "water"))));
        List<Tag> tagsReturned = tagService.findAll();

        assertEquals(new ArrayList<>(Arrays
                .asList(new Tag(1, "sea"),
                        new Tag(2, "water"))), tagsReturned);

        verify(tagDAO).findAll();
    }

    @Test
    void delete_deleteTagById_deletedSuccessfully() throws NotFoundException {
        when(tagDAO.deleteById(1)).thenReturn(true);
        when(tagDAO.deleteTagCertificateConnection(1)).thenReturn(true);
        when(tagDAO.findById(anyInt())).thenReturn(new Tag());

        tagService.delete(1);

        verify(tagDAO).deleteById(1);
        verify(tagDAO).deleteTagCertificateConnection(1);
        verify(tagDAO).findById(anyInt());
    }
}