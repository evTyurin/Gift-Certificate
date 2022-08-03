package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class QueryCriteria {

    private String field;
    private String value;

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("QueryCriteria{");
        stringBuilder.append("field=").append(field).append('\'');
        stringBuilder.append(", value='").append(value).append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}