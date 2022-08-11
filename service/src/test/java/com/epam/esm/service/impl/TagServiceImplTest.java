package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.repository.TagRepository;
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
class TagServiceImplTest {

    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private Validation validation;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(tagRepository, validation);
    }

    @Test
    void find_findTagByCorrectId_findTag() throws NotFoundException {
        Tag expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName("tag");

        when(tagRepository.find(1)).thenReturn(Optional.of(Tag.builder().id(1).name("tag").build()));

        Tag actualTag = tagService.find(1);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void find_findTagByIncorrectId_throwException() {
        when(tagRepository.find(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> tagService.find(1));
    }

    @Test
    void find_findAllTagAmount_returnNumberOfAllTags() {
        tagService.findAmount();
        verify(tagRepository).findAmount();
    }

    @Test
    void delete_deleteTagByCorrectId_deleteTag() throws NotFoundException {
        Tag expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName("tag");
        when(tagRepository.find(1)).thenReturn(Optional.of(expectedTag));
        tagService.delete(1);
        verify(tagRepository).delete(1);
    }

    @Test
    void delete_deleteTagByIncorrectId_throwException() {
        Tag expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName("tag");
        when(tagRepository.find(1)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> tagService.delete(1));
    }

    @Test
    void getMostWidelyUsedTag_getListOfMostWidelyUsedTags_returnListOfTags() {
        Tag expectedTag = new Tag();
        expectedTag.setId(1);
        expectedTag.setName("tag");
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        when(tagRepository.findMostWidelyUsedTag()).thenReturn(expectedTags);
        List<Tag> actualTags = tagService.findMostWidelyUsedTag();
        assertEquals(expectedTags, actualTags);
    }

    @Test
    void create_createDuplicateTag_throwException() {
        Tag expectedTag = new Tag();
        expectedTag.setName("tag");
        when(tagRepository.find("tag")).thenReturn(Optional.of(expectedTag));
        assertThrows(EntityExistException.class, () -> tagService.create(expectedTag));
    }

    @Test
    void create_createTag_createTag() throws EntityExistException {
        Tag expectedTag = new Tag();
        expectedTag.setName("tag");
        when(tagRepository.find("tag")).thenReturn(Optional.empty());
        tagService.create(expectedTag);
        verify(tagRepository).create("tag");
    }

    @Test
    void findAll_findAllTagsWithCorrectPagination_findTags() throws PageElementAmountException, PageNumberException {
        Tag expectedTag = new Tag();
        expectedTag.setName("tag1");
        Tag anotherTag = new Tag();
        anotherTag.setName("tag2");
        List<Tag> expectedTags = new ArrayList<>();
        expectedTags.add(expectedTag);
        expectedTags.add(anotherTag);

        doNothing().when(validation).pageAmountValidation(anyInt(), anyInt(), anyInt());
        doNothing().when(validation).pageElementAmountValidation(anyInt());

        when(tagRepository.findAmount()).thenReturn(10);
        when(tagRepository.findAll(1, 2)).thenReturn(expectedTags);

        List<Tag> actualTags = tagService.findAll(1, 2);
        assertEquals(expectedTags, actualTags);
    }
}