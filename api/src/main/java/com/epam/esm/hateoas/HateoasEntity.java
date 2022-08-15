package com.epam.esm.hateoas;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
     */
    public TagDto addTagLinks(TagDto tagDto) {
        tagDto.add(linkTo(TagController.class)
                .slash(tagDto.getId())
                .withSelfRel());
        return tagDto;
    }

    /**
     * Add links to gift certificate dto and tags dto than includes in gift certificate dto
     *
     * @param giftCertificateDto the gift certificate dto
     * @return the gift certificate dto with links
     */
    public GiftCertificateDto addGiftCertificateLinks(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(GiftCertificateController.class)
                .slash(giftCertificateDto.getId())
                .withSelfRel());

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
     */
    public OrderDto addOrderLinks(OrderDto orderDto) {
        orderDto.add(linkTo(OrderController.class)
                .slash(orderDto.getId())
                .withSelfRel());

        List<GiftCertificateDto> giftCertificatesDto = orderDto.getGiftCertificates();
        for (GiftCertificateDto giftCertificateDto : giftCertificatesDto) {
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
