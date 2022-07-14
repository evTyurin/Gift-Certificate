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
        assertEquals("SELECT DISTINCT certificateId FROM (" +
                        "SELECT gift_certificate.id AS certificateId," +
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
                        " JOIN tag ON tag.id = gift_certificate_has_tag.tag_id" +
                        ") AS t WHERE tagName LIKE 'sea' AND certificateName LIKE '%sea story%'",
                sqlQueryBuilder.createSearchPartOfQuery(queryCriterion));
    }

    @Test
    void create_createOrderedPartOfQuery_returnedQuery() {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        queryCriterion.add(new QueryCriteria("tagName", "asc"));
        queryCriterion.add(new QueryCriteria("certificateName", "desc"));
        assertEquals(" ORDER BY tagName asc, certificateName desc",
                sqlQueryBuilder.createOrderedPartOfQuery(queryCriterion));
    }
}