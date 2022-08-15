package com.epam.esm.dto;

import com.epam.esm.validation.Patchable;
import com.epam.esm.validation.Postable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;
    @NotBlank(message = "blank.field.message", groups = Postable.class)
    @Size(min = 3, max = 50, message = "name.certificate.size.message", groups = {Patchable.class, Postable.class})
    @NotNull(message = "null.field.message", groups = Postable.class)
    private String name;
    @NotBlank(message = "blank.field.message", groups = Postable.class)
    @Size(min = 10, max = 1000, message = "description.size.message", groups = {Patchable.class, Postable.class})
    @NotNull(message = "null.field.message", groups = Postable.class)
    private String description;
    @Min(value = 0, message = "not.positive.price.message", groups = {Patchable.class, Postable.class})
    @Max(value = 10000, message = "not.positive.price.message", groups = {Patchable.class, Postable.class})
    @NotNull(message = "null.field.message", groups = Postable.class)
    private Double price;
    @Min(value = 0, message = "not.positive.duration.message", groups = {Patchable.class, Postable.class})
    @Max(value = 10000, message = "not.positive.duration.message", groups = {Patchable.class, Postable.class})
    @NotNull(message = "null.field.message", groups = Postable.class)
    private Integer duration;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @NotNull(message = "null.field.message", groups = Postable.class)
    private LocalDateTime createDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateDate;
    @Valid
    @NotNull(message = "blank.field.message", groups = Postable.class)
    private List<TagDto> tags;

    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }
}