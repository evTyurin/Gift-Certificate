package com.epam.esm.service.util;

import com.epam.esm.entity.QueryCriteria;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class SqlQueryBuilder {

    public SqlQueryBuilder() {
    }

    public String createSearchPartOfQuery(List<QueryCriteria> queryCriteria) {
        String READ_ALL_GIFT_CERTIFICATE_WITH_TAGS = "SELECT" +
                " gift_certificate.id AS certificateId," +
                " gift_certificate.name AS certificateName," +
                " gift_certificate.description AS description," +
                " gift_certificate.price AS price," +
                " gift_certificate.duretion AS duration," +
                " gift_certificate.create_date AS createDate," +
                " gift_certificate.last_update_date AS lastUpdateDate," +
                " tag.name AS tagName" +
                " FROM gift_certificate JOIN" +
                " gift_certificate_has_tag ON" +
                " gift_certificate_has_tag.gift_certificate_id = gift_certificate.id" +
                " JOIN tag ON tag.id = gift_certificate_has_tag.tag_id";

        StringBuilder query = new StringBuilder();
        query.append("SELECT DISTINCT certificateId FROM (")
                .append(READ_ALL_GIFT_CERTIFICATE_WITH_TAGS).append(") AS t");

        if (queryCriteria.size() > 0) {
            query.append(" WHERE ");
        }
        Iterator<QueryCriteria> iterator = queryCriteria.iterator();
        while (iterator.hasNext()) {
            QueryCriteria criteriaIterator = iterator.next();
            if (criteriaIterator.getField().equals("tagName")) {
                query.append(criteriaIterator.getField()).append(" LIKE '")
                        .append(criteriaIterator.getValue()).append("'");
            } else {
                query.append(criteriaIterator.getField()).append(" LIKE '%")
                        .append(criteriaIterator.getValue()).append("%'");
            }
            if (iterator.hasNext()) {
                query.append(" AND ");
            }
        }
        return query.toString();
    }

    public String createOrderedPartOfQuery(List<QueryCriteria> queryCriteria) {
        StringBuilder query = new StringBuilder();
        if (queryCriteria.size() > 0) {
            query.append(" ORDER BY");
        }
        Iterator<QueryCriteria> iterator = queryCriteria.iterator();
        while (iterator.hasNext()) {
            QueryCriteria criteriaIterator = iterator.next();
            query.append(" ").append(criteriaIterator.getField())
                    .append(" ").append(criteriaIterator.getValue());
            if (iterator.hasNext()) {
                query.append(",");
            }
        }
        return query.toString();
    }
}