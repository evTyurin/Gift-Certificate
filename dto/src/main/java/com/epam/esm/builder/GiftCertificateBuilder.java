package com.epam.esm.builder;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@Validated
public class GiftCertificateBuilder {
    private final TagBuilder tagBuilder;

    public GiftCertificateBuilder(TagBuilder tagBuilder) {
        this.tagBuilder = tagBuilder;
    }

    public GiftCertificate build(@Valid GiftCertificateDto giftCertificateDto) {
        if(giftCertificateDto.getTags() == null) {
            giftCertificateDto.setTags(new ArrayList<>());
        }
        return GiftCertificate
                .builder()
                .name(giftCertificateDto.getName())
                .price(giftCertificateDto.getPrice())
                .description(giftCertificateDto.getDescription())
                .duration(giftCertificateDto.getDuration())
                .tags(tagBuilder.build(giftCertificateDto))
                .build();
    }

    public GiftCertificateDto build(GiftCertificate giftCertificate) {
        return GiftCertificateDto
                .builder()
                .id(giftCertificate.getId())
                .description(giftCertificate.getDescription())
                .name(giftCertificate.getName())
                .duration(giftCertificate.getDuration())
                .price(giftCertificate.getPrice())
                .createDate(giftCertificate.getCreateDate())
                .lastUpdateDate(giftCertificate.getLastUpdateDate())
                .tags(giftCertificate
                        .getTags()
                        .stream()
                        .map(tagBuilder::build)
                        .collect(Collectors.toList()))
                .build();
    }
}