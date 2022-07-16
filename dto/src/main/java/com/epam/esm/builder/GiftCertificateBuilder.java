package com.epam.esm.builder;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Validated
public class GiftCertificateBuilder {
    private final TagBuilder tagBuilder;

    @Autowired
    public GiftCertificateBuilder(TagBuilder tagBuilder) {
        this.tagBuilder = tagBuilder;
    }

    public GiftCertificate build(@Valid GiftCertificateDto dto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setName(dto.getName());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setDuretion(dto.getDuration());
        giftCertificate.setTags(tagBuilder.build(dto));
        return giftCertificate;
    }

    public GiftCertificateDto build(GiftCertificate giftCertificate) {
        GiftCertificateDto dto = new GiftCertificateDto();
        dto.setId(giftCertificate.getId());
        dto.setDescription(giftCertificate.getDescription());
        dto.setName(giftCertificate.getName());
        dto.setDuration(giftCertificate.getDuretion());
        dto.setPrice(giftCertificate.getPrice());
        dto.setCreateDate(giftCertificate.getCreateDate());
        dto.setLastUpdateDate(giftCertificate.getLastUpdateDate());

        List<TagDto> tagsDto = giftCertificate.getTags()
                .stream().map(tagBuilder::build)
                .collect(Collectors.toList());
        dto.setTags(tagsDto);
        return dto;
    }
}
