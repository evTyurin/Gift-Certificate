package com.epam.esm.controller;

import com.epam.esm.builder.GiftCertificateBuilder;
import com.epam.esm.controller.constants.ControllerConstants;
import com.epam.esm.controller.util.UtilQueryCriteriaBuilder;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.ExpectationFailedException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.hateoas.HateoasEntity;
import com.epam.esm.hateoas.PaginationHateoas;
import com.epam.esm.service.GiftCertificateService;

import com.epam.esm.validation.Patchable;
import com.epam.esm.validation.QueryCriteriaValidator;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * RestController that handles requests to /certificates url
 * Contains operations with gift certificates
 */
@RestController
@RequestMapping("certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateBuilder giftCertificateBuilder;
    private final UtilQueryCriteriaBuilder utilQueryCriteriaBuilder;
    private final QueryCriteriaValidator queryCriteriaValidator;
    private final HateoasEntity hateoasEntity;
    private final PaginationHateoas paginationHateoas;

    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateBuilder giftCertificateBuilder,
                                     UtilQueryCriteriaBuilder utilQueryCriteriaBuilder,
                                     QueryCriteriaValidator queryCriteriaValidator,
                                     HateoasEntity hateoasEntity,
                                     PaginationHateoas paginationHateoas) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateBuilder = giftCertificateBuilder;
        this.utilQueryCriteriaBuilder = utilQueryCriteriaBuilder;
        this.queryCriteriaValidator = queryCriteriaValidator;
        this.hateoasEntity = hateoasEntity;
        this.paginationHateoas = paginationHateoas;
    }

    /**
     * Search GiftCertificates by params that come from url.
     * If params for search are empty this method returns all gift certificates.
     *
     * @param tagNames        names of tags
     * @param sortCriteria    criteria for sorting
     * @param certificateName name of the gift certificate
     * @param description     description of the gift certificate
     * @param page            number of page being viewed. It equals 1 by default
     * @param size            amount of elements per page. It equals 3 by default
     * @return list of gift certificate dto
     * @throws ExpectationFailedException indicates that invalid parameters in url used for search
     * @throws NotFoundException          indicates that gift certificate with this id not exist
     * @throws EntityExistException       indicates that gift certificate with this name already exist
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     */
    @GetMapping
    public CollectionModel<GiftCertificateDto> find(
            @RequestParam(name = "tagNames", required = false) String tagNames,
            @RequestParam(name = "sortCriteria", required = false) String sortCriteria,
            @RequestParam(name = "certificateName", required = false) String certificateName,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = ControllerConstants.SIZE) int size) throws
            ExpectationFailedException,
            NotFoundException,
            PageElementAmountException,
            PageNumberException,
            EntityExistException {
        List<QueryCriteria> searchQueryCriteria = utilQueryCriteriaBuilder.createSearchCriteria(tagNames, certificateName, description);
        queryCriteriaValidator.searchCriteriaValidation(searchQueryCriteria);
        List<QueryCriteria> sortQueryCriteria = utilQueryCriteriaBuilder.createSortOrder(sortCriteria);
        queryCriteriaValidator.sortCriteriaValidation(sortQueryCriteria);
        List<GiftCertificate> giftCertificates = giftCertificateService.find(searchQueryCriteria, sortQueryCriteria, page, size);
        List<GiftCertificateDto> giftCertificatesDto = new ArrayList<>();
        for (GiftCertificate giftCertificate : giftCertificates) {
            giftCertificatesDto.add(hateoasEntity.addGiftCertificateLinks(giftCertificateBuilder.build(giftCertificate)));
        }
        List<Link> links = paginationHateoas.addPaginationGiftCertificateLinks(
                tagNames,
                sortCriteria,
                certificateName,
                description,
                size,
                giftCertificateService.countByCriterion(searchQueryCriteria, sortQueryCriteria),
                page);
        return CollectionModel.of(giftCertificatesDto, links);
    }

    /**
     * Get gift certificate dto by id
     *
     * @param id the gift certificate id
     * @return the gift certificate dto
     * @throws NotFoundException    indicates that gift certificate with this id not exist
     * @throws EntityExistException indicates that gift certificate with this name already exist
     */
    @GetMapping("{id}")
    public GiftCertificateDto find(@PathVariable int id) throws NotFoundException {
        return hateoasEntity.addGiftCertificateLinks(giftCertificateBuilder.build(giftCertificateService.find(id)));
    }

    /**
     * Add new gift certificate
     *
     * @param giftCertificateDto the gift certificate dto
     * @throws EntityExistException indicates that gift certificate with this name already exist
     */
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody GiftCertificateDto giftCertificateDto) throws EntityExistException {
        giftCertificateService.create(giftCertificateBuilder.build(giftCertificateDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update of gift certificate with tags
     *
     * @param id                 the gift certificate id
     * @param giftCertificateDto the gift certificate dto
     * @throws EntityExistException indicates that gift certificate with this name already exist
     * @throws NotFoundException    indicates that gift certificate with this id not exist
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable int id, @Valid @RequestBody GiftCertificateDto giftCertificateDto) throws EntityExistException, NotFoundException {
        giftCertificateService.update(id, giftCertificateBuilder.build(giftCertificateDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Partial update of gift certificate with tags
     *
     * @param id                 the gift certificate id
     * @param giftCertificateDto the gift certificate dto
     * @throws EntityExistException indicates that gift certificate with this name already exist
     * @throws NotFoundException    indicates that gift certificate with this id not exist
     */
    @PatchMapping("{id}")
    public ResponseEntity<Void> partialUpdate(@PathVariable int id, @Validated(Patchable.class) @RequestBody GiftCertificateDto giftCertificateDto) throws EntityExistException, NotFoundException {
        giftCertificateService.partialUpdate(id, giftCertificateBuilder.build(giftCertificateDto));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete gift certificate by id
     *
     * @param id the gift certificate id
     * @throws NotFoundException indicates that gift certificate with this id not exist
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws NotFoundException {
        giftCertificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}