package com.epam.esm.controller;

import com.epam.esm.builder.GiftCertificateBuilder;
import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.ExpectationFailedException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.controller.util.UtilQueryCriteriaBuilder;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.validation.QueryCriteriaValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * RestController that handles requests to /certificates url.
 * Contain create/read/update/delete operations with gift certificates
 */
@RestController
@RequestMapping("certificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;
    private final GiftCertificateBuilder giftCertificateBuilder;
    private final UtilQueryCriteriaBuilder utilQueryCriteriaBuilder;
    private final QueryCriteriaValidator queryCriteriaValidator;

    public GiftCertificateController(GiftCertificateService giftCertificateService,
                                     GiftCertificateBuilder giftCertificateBuilder,
                                     UtilQueryCriteriaBuilder utilQueryCriteriaBuilder,
                                     QueryCriteriaValidator queryCriteriaValidator) {
        this.giftCertificateService = giftCertificateService;
        this.giftCertificateBuilder = giftCertificateBuilder;
        this.utilQueryCriteriaBuilder = utilQueryCriteriaBuilder;
        this.queryCriteriaValidator = queryCriteriaValidator;
    }

    /**
     * Search GiftCertificates by params that come from url.
     * If params are empty this method returns all GiftCertificates
     *
     * @param params map of search criteria. First param - name of search criteria,
     *               second param - value of search criteria
     * @return return list of gift certificate dto
     */
    @GetMapping
    public List<GiftCertificateDto> search(@RequestParam(required = false) Map<String, String> params) throws ExpectationFailedException {
        List<QueryCriteria> searchQueryCriteria = utilQueryCriteriaBuilder.createSearchCriteria(params);
        queryCriteriaValidator.searchCriteriaValidation(searchQueryCriteria);
        List<QueryCriteria> sortQueryCriteria = utilQueryCriteriaBuilder.createSortOrder(params);
        queryCriteriaValidator.sortCriteriaValidation(sortQueryCriteria);
        List<GiftCertificate> giftCertificates = giftCertificateService.find(searchQueryCriteria, sortQueryCriteria);
        return giftCertificates.stream()
                .map(giftCertificateBuilder::build)
                .collect(Collectors.toList());
    }

    /**
     * Get gift certificate dto by id
     *
     * @param id the gift certificate id
     * @return the gift certificate dto
     * @throws NotFoundException indicates that gift certificate with this id not exist
     */
    @GetMapping("{id}")
    public GiftCertificateDto search(@PathVariable int id) throws NotFoundException {
        return giftCertificateBuilder.build(giftCertificateService.find(id));
    }

    /**
     * Add certificate and tags
     *
     * @param giftCertificateDto the gift certificate dto
     * @throws EntityExistException indicates that gift certificate with this name already exist
     */
    @PostMapping
    public void addCertificate(@Valid @RequestBody GiftCertificateDto giftCertificateDto) throws EntityExistException {
        GiftCertificate giftCertificate = giftCertificateBuilder.build(giftCertificateDto);
        giftCertificateService.create(giftCertificate);
    }

    /**
     * Update certificate and tags
     *
     * @param id                 the gift certificate id
     * @param giftCertificateDto the gift certificate dto
     * @throws EntityExistException indicates that gift certificate with this name already exist
     * @throws NotFoundException    indicates that gift certificate with this id not exist
     */
    @PutMapping("{id}")
    public void update(@PathVariable int id, @Valid @RequestBody GiftCertificateDto giftCertificateDto) throws EntityExistException, NotFoundException {
        GiftCertificate giftCertificate = giftCertificateBuilder.build(giftCertificateDto);
        giftCertificateService.update(id, giftCertificate);
    }

    /**
     * Delete GiftCertificate by id
     *
     * @param id the gift certificate id
     * @throws NotFoundException indicates that gift certificate with this id not exist
     */
    @DeleteMapping("{id}")
    public void deleteById(@PathVariable int id) throws NotFoundException {
        giftCertificateService.delete(id);
    }
}