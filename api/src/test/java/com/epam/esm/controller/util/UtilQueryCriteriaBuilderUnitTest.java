package com.epam.esm.controller.util;

import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.exception.ExpectationFailedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UtilQueryCriteriaBuilderUnitTest {
    @InjectMocks
    private UtilQueryCriteriaBuilder utilQueryCriteriaBuilder;

    @Test
    void createSortOrder_createIncorrectSortOrder_returnedTrue() throws ExpectationFailedException {
        Map<String, String> sortCriterion = new HashMap<>();
        sortCriterion.put("sort", "createDate-desc");
        List<QueryCriteria> queryCriterionExpected = new ArrayList<>();
        queryCriterionExpected.add(new QueryCriteria("createDate", "desc"));

        assertEquals(queryCriterionExpected, utilQueryCriteriaBuilder.createSortOrder(sortCriterion));
    }

    @ParameterizedTest
    @ValueSource(strings = {"-createDate-desc",
            "createDate--desc",
            "createDate-desc&-tagName-desc"})
    void createSortOrder_createIncorrectSortOrder_returnedException(String criteria) {
        Map<String, String> sortCriterion = new HashMap<>();
        sortCriterion.put("sort", criteria);

        assertThrows(ExpectationFailedException.class, () ->
                utilQueryCriteriaBuilder.createSortOrder(sortCriterion));
    }
}