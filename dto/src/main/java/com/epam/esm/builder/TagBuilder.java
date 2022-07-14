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

    public TagBuilder() {
    }

    public List<Tag> build(@Valid GiftCertificateDto dto) {
        return dto.getTags().stream().map(this::build).collect(Collectors.toList());
    }

    public Tag build(@Valid TagDto dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setId(dto.getId());
        return tag;
    }

    public TagDto build(Tag tag) {
        TagDto dto = new TagDto();
        dto.setName(tag.getName());
        dto.setId(tag.getId());
        return dto;
    }
}