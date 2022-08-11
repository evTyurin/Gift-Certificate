package com.epam.esm.controller.util;

import com.epam.esm.entity.QueryCriteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The query criteria builder used for building queries for searching and sorting
 * gift certificates by different criterion
 */
@Component
public class UtilQueryCriteriaBuilder {

    /**
     * Create part of query for sorting gift certificates by sort criterion
     *
     * @param criteria criteria used for sort
     * @return list of query criterion used for sort
     */
    public List<QueryCriteria> createSortOrder(String criteria) {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        if (criteria != null) {
            Arrays.stream(criteria.split(","))
                    .forEach(criteriaValue ->
                            queryCriterion.add(new QueryCriteria(
                                    criteriaValue.substring(0, criteriaValue.indexOf("-")),
                                    criteriaValue.substring(criteriaValue.indexOf("-") + 1))
                            )
                    );
        }
        return queryCriterion;
    }

    /**
     * Create part of query for searching gift certificates by different criterion
     *
     * @param tagNames        names of tags
     * @param certificateName name of gift certificate
     * @param description     description of gist certificate
     * @return list of query criterion used for search
     */
    public List<QueryCriteria> createSearchCriteria(String tagNames,
                                                    String certificateName,
                                                    String description) {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        if (tagNames != null) {
            Arrays.stream(tagNames.split(","))
                    .forEach(criteriaValue ->
                            queryCriterion.add(new QueryCriteria(
                                    "tagName",
                                    criteriaValue)));
        }
        if (certificateName != null) {
            queryCriterion.add(new QueryCriteria(
                    "certificateName",
                    certificateName));
        }
        if (description != null) {
            queryCriterion.add(new QueryCriteria(
                    "description",
                    description));
        }
        return queryCriterion;
    }
}