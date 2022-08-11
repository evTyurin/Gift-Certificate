package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TagDto extends RepresentationModel<TagDto> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;
    @NotBlank(message = "blank.field.message")
    @Size(min = 3, max = 50, message = "name.tag.size.message")
    private String name;

    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }
}