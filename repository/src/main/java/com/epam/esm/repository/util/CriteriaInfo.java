package com.epam.esm.repository.util;

public enum CriteriaInfo {
    GIFT_CERTIFICATE_NAME("certificateName", "name"),
    GIFT_CERTIFICATE_DESCRIPTION("description", "description"),
    GIFT_CERTIFICATE_CREATE_DATE("createDate", "createDate"),
    GIFT_CERTIFICATE_LAST_UPDATE_DATE("lastUpdateDate", "lastUpdateDate"),
    TAG_NAME("tagName", "name");

    private final String criteriaName;
    private final String entityFieldName;

    CriteriaInfo(String criteriaName, String entityFieldName) {
        this.criteriaName = criteriaName;
        this.entityFieldName = entityFieldName;
    }

    public static String getEntityFieldName(String criteriaName) {
        for (CriteriaInfo criteriaInfo:CriteriaInfo.values()) {
            if (criteriaInfo.criteriaName.equals(criteriaName)) {
                return criteriaInfo.entityFieldName;
            }
        }
        return criteriaName;
    }
}