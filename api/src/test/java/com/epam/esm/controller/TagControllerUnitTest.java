package com.epam.esm.controller;

import com.epam.esm.builder.TagBuilder;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagControllerUnitTest {
    @InjectMocks
    private TagController tagController;
    @Mock
    private TagService tagService;
    @Mock
    private TagBuilder tagBuilder;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(tagService,
                tagBuilder);
    }

    @Test
    void find_findTagById_returnTag() throws NotFoundException {
        TagDto dto = new TagDto();
        dto.setName("sea");
        dto.setId(1);
        Tag testTag = new Tag();
        testTag.setId(1);
        testTag.setName("sea");
        when(tagService.find(1)).thenReturn(testTag);
        when(tagBuilder.build(testTag)).thenReturn(dto);

        assertEquals(dto, tagController.findById(1));

        verify(tagService).find(1);
        verify(tagBuilder).build(testTag);
    }

    @Test
    void delete_deleteTagById_deletedSuccessfully() throws NotFoundException {
        tagController.delete(1);

        verify(tagService).delete(1);
    }

    @Test
    void getAll_getAllTags_returnedTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        Tag testTag1 = new Tag();
        testTag1.setId(1);
        testTag1.setName("sea");
        Tag testTag2 = new Tag();
        testTag2.setId(2);
        testTag2.setName("water");
        tags.add(testTag1);
        tags.add(testTag2);
        TagDto firstTagDto = new TagDto();
        firstTagDto.setId(1);
        firstTagDto.setName("sea");
        TagDto secondTagDto = new TagDto();
        firstTagDto.setId(2);
        firstTagDto.setName("water");
        ArrayList<TagDto> tagsDto = new ArrayList<>();
        tagsDto.add(firstTagDto);
        tagsDto.add(secondTagDto);

        when(tagService.findAll()).thenReturn(tags);
        when(tagBuilder.build(testTag1)).thenReturn(firstTagDto);
        when(tagBuilder.build(testTag2)).thenReturn(secondTagDto);

        assertEquals(tagController.findAll(), tagsDto);

        verify(tagService).findAll();
        verify(tagBuilder).build(testTag1);
        verify(tagBuilder).build(testTag2);
    }

    @Test
    void create_createTag_createSuccessfully() throws EntityExistException {
        TagDto tagDto = new TagDto();
        tagDto.setId(1);
        tagDto.setName("sea");
        Tag testTag = new Tag();
        testTag.setId(1);
        testTag.setName("sea");

        when(tagBuilder.build(tagDto)).thenReturn(testTag);

        tagBuilder.build(tagDto);
        tagService.create(testTag);

        verify(tagBuilder).build(tagDto);
        verify(tagService).create(testTag);
    }
}