package com.epam.esm.service.util;

import com.epam.esm.entity.QueryCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SqlQueryBuilderUnitTest {
    @InjectMocks
    private SqlQueryBuilder sqlQueryBuilder;

    @Test
    void create_createSearchPartOfQuery_returnedQuery() {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        queryCriterion.add(new QueryCriteria("tagName", "sea"));
        queryCriterion.add(new QueryCriteria("certificateName", "sea story"));
        assertEquals("SELECT id, name, description," +
                        " price, duretion," +
                        " create_date, last_update_date FROM (" +
                        "SELECT" +
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
                        " JOIN tag ON tag.id = gift_certificate_has_tag.tag_id" +
                        ") AS t WHERE tagName LIKE 'sea' AND name LIKE '%sea story%'",
                sqlQueryBuilder.createSearchPartOfQuery(queryCriterion));
    }

    @Test
    void create_createOrderedPartOfQuery_returnedQuery() {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        queryCriterion.add(new QueryCriteria("tagName", "asc"));
        queryCriterion.add(new QueryCriteria("certificateName", "desc"));
        assertEquals(" ORDER BY tagName asc, name desc",
                sqlQueryBuilder.createOrderedPartOfQuery(queryCriterion));
    }
}