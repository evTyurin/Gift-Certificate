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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Tag testTag = new Tag();
        testTag.setId(4);
        testTag.setName("sea");
        when(tagDAO.find(1)).thenReturn(Optional.of(testTag));
        Tag tagReturned = tagService.find(1);

        assertEquals(tagReturned, testTag);

        verify(tagDAO).find(1);
    }

    @Test
    void find_findAllTags_returnedTags() {
        Tag testTag1 = new Tag();
        testTag1.setId(1);
        testTag1.setName("sea");
        Tag testTag2 = new Tag();
        testTag2.setId(2);
        testTag2.setName("water");
        when(tagDAO.findAll()).thenReturn(new ArrayList<>(Arrays
                .asList(testTag1,
                        testTag2)));
        List<Tag> tagsReturned = tagService.findAll();

        assertEquals(new ArrayList<>(Arrays
                .asList(testTag1,
                        testTag2)), tagsReturned);

        verify(tagDAO).findAll();
    }
}