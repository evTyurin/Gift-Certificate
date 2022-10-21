package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.controller.constants.HateoasConstants;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.ExpectationFailedException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class include methods for pagination implementation for instances
 */
@Component
public class PaginationHateoas {

    /**
     * Implement pagination for list of tags dto
     *
     * @param size           amount of elements per page
     * @param page           the number of page being viewed
     * @param elementsAmount the amount of all instances
     * @return list of pagination links
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     * @throws NotFoundException          indicates that tag with this id not exist
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws EntityExistException       indicates that tag with this name already exist
     */
    public List<Link> addPaginationTagLinks(int size,
                                            int page,
                                            int elementsAmount) throws
            PageNumberException,
            NotFoundException,
            PageElementAmountException,
            EntityExistException {
        List<Link> links = new ArrayList<>();
        int pagesAmount = getPagesAmount(elementsAmount, size);
        links.add(linkTo(methodOn(TagController.class)
                .findAll(1, size))
                .withRel(HateoasConstants.FIRST));
        if (hasPreviousPage(page)) {
            links.add(linkTo(methodOn(TagController.class)
                    .findAll(page - 1, size))
                    .withRel(HateoasConstants.PREVIOUS));
        }
        links.add(linkTo(methodOn(TagController.class)
                .findAll(page, size))
                .withSelfRel());
        if (hasNextPage(page, pagesAmount)) {
            links.add(linkTo(methodOn(TagController.class)
                    .findAll(page + 1, size))
                    .withRel(HateoasConstants.NEXT));
        }
        links.add(linkTo(methodOn(TagController.class)
                .findAll(pagesAmount, size))
                .withRel(HateoasConstants.LAST));
        return links;
    }

    /**
     * Implement pagination for list of gift certificates dto
     *
     * @param tagName         name of the tag
     * @param sort            sort criteria
     * @param certificateName name of the gift certificate
     * @param description     description of the gift certificate
     * @param size            amount of elements per page
     * @param page            the number of page being viewed
     * @param elementsAmount  the amount of all instances
     * @return list of pagination links
     * @throws ExpectationFailedException indicates that invalid parameters in url used for search
     * @throws NotFoundException          indicates that gift certificate with this id not exist
     * @throws EntityExistException       indicates that gift certificate with this name already exist
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     */
    public List<Link> addPaginationGiftCertificateLinks(String tagName,
                                                        String sort,
                                                        String certificateName,
                                                        String description,
                                                        int size,
                                                        int elementsAmount,
                                                        int page) throws
            PageNumberException,
            NotFoundException,
            PageElementAmountException,
            EntityExistException,
            ExpectationFailedException {
        List<Link> links = new ArrayList<>();
        int pagesAmount = getPagesAmount(elementsAmount, size);
        links.add(linkTo(methodOn(GiftCertificateController.class)
                .find(tagName,
                        sort,
                        certificateName,
                        description,
                        1,
                        size))
                .withRel(HateoasConstants.FIRST)
                .expand());

        if (hasPreviousPage(page)) {
            links.add(linkTo(methodOn(GiftCertificateController.class)
                    .find(tagName,
                            sort,
                            certificateName,
                            description,
                            page - 1,
                            size))
                    .withRel(HateoasConstants.PREVIOUS)
                    .expand());
        }
        links.add(linkTo(methodOn(GiftCertificateController.class)
                .find(tagName,
                        sort,
                        certificateName,
                        description,
                        page,
                        size))
                .withSelfRel()
                .expand());
        if (hasNextPage(page, pagesAmount)) {
            links.add(linkTo(methodOn(GiftCertificateController.class)
                    .find(tagName,
                            sort,
                            certificateName,
                            description,
                            page + 1,
                            size))
                    .withRel(HateoasConstants.NEXT)
                    .expand());
        }
        links.add(linkTo(methodOn(GiftCertificateController.class)
                .find(tagName,
                        sort,
                        certificateName,
                        description,
                        pagesAmount,
                        size))
                .withRel(HateoasConstants.LAST)
                .expand());
        return links;
    }

    /**
     * Implement pagination for list of orders dto
     *
     * @param size           amount of elements per page
     * @param page           the number of page being viewed
     * @param elementsAmount the amount of all instances
     * @return list of pagination links
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     * @throws NotFoundException          indicates that order with this id not exist
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws EntityExistException       indicates that order with this name already exist
     */
    public List<Link> addPaginationOrderLinks(int size,
                                              int page,
                                              int elementsAmount) throws
            PageNumberException,
            NotFoundException,
            PageElementAmountException,
            EntityExistException {
        List<Link> links = new ArrayList<>();
        int pagesAmount = getPagesAmount(elementsAmount, size);
        links.add(linkTo(methodOn(OrderController.class)
                .findAll(1, size))
                .withRel(HateoasConstants.FIRST));
        if (hasPreviousPage(page)) {
            links.add(linkTo(methodOn(OrderController.class)
                    .findAll(page - 1, size))
                    .withRel(HateoasConstants.PREVIOUS));
        }
        links.add(linkTo(methodOn(OrderController.class)
                .findAll(page, size))
                .withSelfRel());
        if (hasNextPage(page, pagesAmount)) {
            links.add(linkTo(methodOn(OrderController.class)
                    .findAll(page + 1, size))
                    .withRel(HateoasConstants.NEXT));
        }
        links.add(linkTo(methodOn(OrderController.class)
                .findAll(pagesAmount, size))
                .withRel(HateoasConstants.LAST));
        return links;
    }

    /**
     * Implement pagination for list of users dto
     *
     * @param size           amount of elements per page
//     * @param page           the number of page being viewed
     * @param elementsAmount the amount of all instances
     * @return list of pagination links
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     * @throws NotFoundException          indicates that user with this id not exist
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws EntityExistException       indicates that user with this name already exist
     */
//    public List<Link> addPaginationUserLinks(int size,
//                                             int page,
//                                             int elementsAmount) throws
//            PageNumberException,
//            NotFoundException,
//            PageElementAmountException,
//            EntityExistException {
//        List<Link> links = new ArrayList<>();
//        int pagesAmount = getPagesAmount(elementsAmount, size);
//        links.add(linkTo(methodOn(UserController.class)
//                .findAll(1, size))
//                .withRel(HateoasConstants.FIRST));
//        if (hasPreviousPage(page)) {
//            links.add(linkTo(methodOn(UserController.class)
//                    .findAll(page - 1, size))
//                    .withRel(HateoasConstants.PREVIOUS));
//        }
//        links.add(linkTo(methodOn(UserController.class)
//                .findAll(page, size))
//                .withSelfRel());
//        if (hasNextPage(page, pagesAmount)) {
//            links.add(linkTo(methodOn(UserController.class)
//                    .findAll(page + 1, size))
//                    .withRel(HateoasConstants.NEXT));
//        }
//        links.add(linkTo(methodOn(UserController.class)
//                .findAll(pagesAmount, size))
//                .withRel(HateoasConstants.LAST));
//        return links;
//    }

    private int getPagesAmount(int elementsAmount, int size) {
        return (int) Math.ceil((double) elementsAmount / size);
    }

    private boolean hasNextPage(int page, int pagesAmount) {
        return page < pagesAmount;
    }

    private boolean hasPreviousPage(int page) {
        return page > 1;
    }
}
