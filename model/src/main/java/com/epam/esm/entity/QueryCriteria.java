package com.epam.esm.entity;

import java.util.Objects;

public class QueryCriteria {
    private String field;
    private String value;

    public QueryCriteria() {
    }

    public QueryCriteria(String field, String value) {
        this.field = field;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("QueryCriteria{");
        stringBuilder.append("field=").append(field).append('\'');
        stringBuilder.append(", value='").append(value).append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryCriteria)) return false;
        QueryCriteria criteria = (QueryCriteria) o;
        return getField().equals(criteria.getField()) && getValue().equals(criteria.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField(), getValue());
    }
}