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
        when(tagService.find(1)).thenReturn(new Tag(1, "sea"));
        when(tagBuilder.build(new Tag(1, "sea"))).thenReturn(dto);

        assertEquals(dto, tagController.findById(1));

        verify(tagService).find(1);
        verify(tagBuilder).build(new Tag(1, "sea"));
    }

    @Test
    void delete_deleteTagById_deletedSuccessfully() throws NotFoundException {
        tagController.delete(1);

        verify(tagService).delete(1);
    }

    @Test
    void getAll_getAllTags_returnedTags() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1, "sea"));
        tags.add(new Tag(2, "water"));
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
        when(tagBuilder.build(new Tag(1, "sea"))).thenReturn(firstTagDto);
        when(tagBuilder.build(new Tag(2, "water"))).thenReturn(secondTagDto);

        assertEquals(tagController.findAll(), tagsDto);

        verify(tagService).findAll();
        verify(tagBuilder).build(new Tag(1, "sea"));
        verify(tagBuilder).build(new Tag(2, "water"));
    }

    @Test
    void create_createTag_createSuccessfully() throws EntityExistException {
        TagDto tagDto = new TagDto();
        tagDto.setId(1);
        tagDto.setName("sea");

        when(tagBuilder.build(tagDto)).thenReturn(new Tag(1, "sea"));

        tagBuilder.build(tagDto);
        tagService.create(new Tag(1, "sea"));

        verify(tagBuilder).build(tagDto);
        verify(tagService).create(new Tag(1, "sea"));
    }
}
