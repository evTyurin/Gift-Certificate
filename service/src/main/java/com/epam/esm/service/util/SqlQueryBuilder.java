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
                " gift_certificate.id," +
                " gift_certificate.name," +
                " gift_certificate.description," +
                " gift_certificate.price," +
                " gift_certificate.duretion," +
                " gift_certificate.create_date," +
                " gift_certificate.last_update_date," +
                " tag.name AS tagName" +
                " FROM gift_certificate JOIN" +
                " gift_certificate_has_tag ON" +
                " gift_certificate_has_tag.gift_certificate_id = gift_certificate.id" +
                " JOIN tag ON tag.id = gift_certificate_has_tag.tag_id";

        StringBuilder query = new StringBuilder();
        query.append("SELECT id, name, description," +
                " price, duretion," +
                " create_date, last_update_date FROM (")
                .append(READ_ALL_GIFT_CERTIFICATE_WITH_TAGS).append(") AS t");

        if (!queryCriteria.isEmpty()) {
            query.append(" WHERE ");
        }
        Iterator<QueryCriteria> iterator = queryCriteria.iterator();
        while (iterator.hasNext()) {
            QueryCriteria criteriaIterator = iterator.next();

            if (criteriaIterator.getField().equals("tagName")) {
                query.append(CriteriaInfo.getEntityFieldName(criteriaIterator.getField())).append(" LIKE '")
                        .append(criteriaIterator.getValue()).append("'");
            } else {
                query.append(CriteriaInfo.getEntityFieldName(criteriaIterator.getField())).append(" LIKE '%")
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
        if (!queryCriteria.isEmpty()) {
            query.append(" ORDER BY");
        }
        Iterator<QueryCriteria> iterator = queryCriteria.iterator();
        while (iterator.hasNext()) {
            QueryCriteria criteriaIterator = iterator.next();
            query.append(" ").append(CriteriaInfo.getEntityFieldName(criteriaIterator.getField()))
                    .append(" ").append(criteriaIterator.getValue());
            if (iterator.hasNext()) {
                query.append(",");
            }
        }
        return query.toString();
    }
}