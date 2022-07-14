package com.epam.esm.controller.util;

import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.exception.ExpectationFailedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The query criteria builder used for building queries for searching and sorting
 * gift certificates by different criterion
 */
@Component
public class UtilQueryCriteriaBuilder {
    public UtilQueryCriteriaBuilder() {
    }

    /**
     * Create part of query for sorting gift certificates by different criterion
     *
     * @param allCriterion criterion used for sort
     * @return part of query used for sort
     */
    public List<QueryCriteria> createSortOrder(Map<String, String> allCriterion) throws ExpectationFailedException {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        List<String> sortCriterion = allCriterion.keySet().stream()
                .filter(criteria -> criteria.equals("sort"))
                .map(criteria -> allCriterion.get("sort"))
                .map(criteria -> Arrays.stream(criteria.split("[,]"))
                        .collect(Collectors.toList()))
                .findFirst().orElse(new ArrayList<>());
        for (String criteria : sortCriterion) {
            if (criteria.length() - criteria.replace("-", "").length() != 1) {
                throw new ExpectationFailedException("40017");
            }
            queryCriterion.add(new QueryCriteria(criteria.substring(0, criteria.indexOf("-")),
                    criteria.substring(criteria.indexOf("-") + 1)));
        }
        return queryCriterion;
    }

    /**
     * Create part of query for searching gift certificates by different criterion
     *
     * @param searchCriterion criterion used for search
     * @return part of query used for search
     */
    public List<QueryCriteria> createSearchCriteria(Map<String, String> searchCriterion) {
        return searchCriterion.keySet().stream().filter(criteria -> !criteria.equals("sort"))
                .map(criteria -> new QueryCriteria(criteria, searchCriterion.get(criteria)))
                .collect(Collectors.toList());
    }
}