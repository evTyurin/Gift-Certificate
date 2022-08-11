package com.epam.esm.builder;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagBuilder {

    public List<Tag> build(@Valid GiftCertificateDto giftCertificateDto) {
        return giftCertificateDto
                .getTags()
                .stream()
                .map(this::build)
                .collect(Collectors.toList());
    }

    public Tag build(@Valid TagDto tagDto) {
        return Tag
                .builder()
                .id(tagDto.getId())
                .name(tagDto.getName())
                .build();
    }

    public TagDto build(Tag tag) {
        return TagDto
                .builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}