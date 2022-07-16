package com.epam.esm.service.util;

public enum CriteriaInfo {
    GIFT_CERTIFICATE_NAME("certificateName", "name"),
    GIFT_CERTIFICATE_DESCRIPTION("description", "description"),
    GIFT_CERTIFICATE_CREATE_DATE("createDate", "create_date"),
    TAG_NAME("tagName", "tagName");

    private final String criteriaName;
    private final String entityFieldName;

    CriteriaInfo(String criteriaName, String entityFieldName) {
        this.criteriaName = criteriaName;
        this.entityFieldName = entityFieldName;
    }

    public static String getEntityFieldName(String criteriaName) {
        for (CriteriaInfo criteriaInfo : CriteriaInfo.values()) {
            if (criteriaInfo.criteriaName.equals(criteriaName)) {
                return criteriaInfo.entityFieldName;
            }
        }
        return criteriaName;
    }
}