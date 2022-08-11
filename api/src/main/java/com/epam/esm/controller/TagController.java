package com.epam.esm.controller;

import com.epam.esm.builder.TagBuilder;
import com.epam.esm.controller.constants.ControllerConstants;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.hateoas.HateoasEntity;
import com.epam.esm.hateoas.PaginationHateoas;
import com.epam.esm.service.TagService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * RestController that handles requests to /tags url.
 * Contains operations with tags
 */
@RestController
@RequestMapping("tags")
public class TagController {
    private final TagService tagService;
    private final TagBuilder tagBuilder;
    private final PaginationHateoas paginationHateoas;
    private final HateoasEntity hateoasEntity;

    public TagController(TagService tagService,
                         TagBuilder tagBuilder,
                         PaginationHateoas paginationHateoas,
                         HateoasEntity hateoasEntity) {
        this.tagService = tagService;
        this.tagBuilder = tagBuilder;
        this.paginationHateoas = paginationHateoas;
        this.hateoasEntity = hateoasEntity;
    }

    /**
     * Get list of all tags
     *
     * @param page the number of page being viewed. It equals 1 by default
     * @param size amount of elements per page. It equals 3 by default
     * @return list of tags dto
     * @throws NotFoundException          indicates that tag with this id not exist
     * @throws EntityExistException       indicates that tag with this name already exist
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     */
    @GetMapping
    public CollectionModel<TagDto> findAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "size", defaultValue = ControllerConstants.SIZE) int size) throws
            PageElementAmountException,
            PageNumberException,
            NotFoundException,
            EntityExistException {
        List<Tag> tags = tagService.findAll(page, size);
        List<TagDto> tagsDto = new ArrayList<>();
        for (Tag tag : tags) {
            tagsDto.add(hateoasEntity.addTagLinks(tagBuilder.build(tag)));
        }
        List<Link> links = paginationHateoas.addPaginationTagLinks(size, page, tagService.findAmount());
        return CollectionModel.of(tagsDto, links);
    }

    /**
     * Get tag dto by id
     *
     * @param id the tag id
     * @return the tag dto
     * @throws NotFoundException indicates that tag with this id not exist
     */
    @GetMapping("{id}")
    public TagDto find(@PathVariable int id) throws NotFoundException {
        return hateoasEntity.addTagLinks(tagBuilder.build(tagService.find(id)));
    }

    /**
     * Create tag
     *
     * @param tagDto the tag dto
     * @throws EntityExistException indicates that tag with this name already exist
     */
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody TagDto tagDto) throws EntityExistException {
        tagService.create(tagBuilder.build(tagDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get the most widely used tag
     *
     * @return the tag dto
     * @throws NotFoundException indicates that tag with this name already exist
     */
    @GetMapping("/widest")
    public List<TagDto> getMostWidelyUsedTag() throws NotFoundException {
        List<TagDto> tagsDto = new ArrayList<>();
        for (Tag tag : tagService.findMostWidelyUsedTag()) {
            tagsDto.add(hateoasEntity.addTagLinks(tagBuilder.build(tag)));
        }
        return tagsDto;
    }

    /**
     * Delete tag by id
     *
     * @param id the tag id
     * @throws NotFoundException indicates that tag with this id not exist
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws NotFoundException {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}