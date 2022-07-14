package com.epam.esm.controller;

import com.epam.esm.builder.TagBuilder;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.TagService;
import com.epam.esm.entity.Tag;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * RestController that handles requests to /tags url.
 * Contain create/read/delete operations with tags
 */
@RestController
@RequestMapping("tags")
public class TagController {
    private final TagService tagService;
    private final TagBuilder tagBuilder;

    public TagController(TagService tagService,
                         TagBuilder tagBuilder) {
        this.tagService = tagService;
        this.tagBuilder = tagBuilder;
    }

    /**
     * Get list of all tags
     *
     * @return list of tag dto
     */
    @GetMapping
    public List<TagDto> findAll() {
        List<Tag> tags = tagService.findAll();
        List<TagDto> tagsDto = new ArrayList<>();
        tags.forEach(tag -> tagsDto.add(tagBuilder.build(tag)));
        return tagsDto;
    }

    /**
     * Find tag dto by id
     *
     * @param id the tag id
     * @return the tag dto
     * @throws NotFoundException indicates that tag with this id not exist
     */
    @GetMapping("{id}")
    public TagDto findById(@PathVariable int id) throws NotFoundException {
        return tagBuilder.build(tagService.find(id));
    }

    /**
     * Create tag
     *
     * @param tagDto the tag dto
     * @throws EntityExistException indicates that tag with this name already exist
     */
    @PostMapping
    public void create(@Valid @RequestBody TagDto tagDto) throws EntityExistException {
        tagService.create(tagBuilder.build(tagDto));
    }

    /**
     * Delete tag by id
     *
     * @param id the tag id
     * @throws NotFoundException indicates that tag with this id not exist
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) throws NotFoundException {
        tagService.delete(id);
    }
}