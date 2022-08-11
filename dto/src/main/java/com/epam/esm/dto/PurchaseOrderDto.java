package com.epam.esm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseOrderDto {
    @Positive(message = "not.positive.user.id.message")
    private int userId;

    @NotEmpty(message = "blank.field.message")
    private List<Integer> giftCertificateIds;
}
