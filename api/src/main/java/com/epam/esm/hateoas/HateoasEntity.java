package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.controller.constants.HateoasConstants;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class include methods for hateoas implementation for instances
 */
@Component
public class HateoasEntity {

    /**
     * Add links to tag dto
     *
     * @param tagDto the tag dto
     * @return the tag dto with links
     * @throws NotFoundException indicates that tag with this id not exist
     */
    public TagDto addTagLinks(TagDto tagDto) throws NotFoundException {
        tagDto.add(linkTo(TagController.class)
                .slash(tagDto.getId())
                .withSelfRel());
        tagDto.add(linkTo(methodOn(TagController.class)
                .delete(tagDto.getId()))
                .withRel(HateoasConstants.DELETE));
        return tagDto;
    }

    /**
     * Add links to gift certificate dto and tags dto than includes in gift certificate dto
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto with links
     * @throws NotFoundException indicates that gift certificate with this id not exist
     */
    public GiftCertificateDto addGiftCertificateLinks(GiftCertificateDto giftCertificateDto) throws NotFoundException {
        giftCertificateDto.add(linkTo(GiftCertificateController.class)
                .slash(giftCertificateDto.getId())
                .withSelfRel());
        giftCertificateDto.add(linkTo(methodOn(GiftCertificateController.class)
                .delete(giftCertificateDto.getId()))
                .withRel(HateoasConstants.DELETE));

        List<TagDto> tagsDto = giftCertificateDto.getTags();
        for (TagDto tagDto : tagsDto) {
            addTagLinks(tagDto);
        }
        return giftCertificateDto;
    }

    /**
     * Add links to order dto and gift certificates dto than includes in order dto
     *
     * @param orderDto the order dto
     * @return the order dto with links
     * @throws NotFoundException indicates that order with this id not exist
     */
    public OrderDto addOrderLinks(OrderDto orderDto) throws NotFoundException {
        orderDto.add(linkTo(OrderController.class)
                .slash(orderDto.getId())
                .withSelfRel());

        List<GiftCertificateDto> giftCertificatesDto = orderDto.getGiftCertificates();
        for (GiftCertificateDto giftCertificateDto:giftCertificatesDto) {
            addGiftCertificateLinks(giftCertificateDto);
        }
        addUserLinks(orderDto.getUser());
        return orderDto;
    }

    /**
     * Add links to user dto
     *
     * @param userDto the user dto
     * @return the user dto with links
     */
    public UserDto addUserLinks(UserDto userDto) {
        return userDto.add(linkTo(UserController.class)
                        .slash(userDto.getId())
                        .withSelfRel());
    }
}
