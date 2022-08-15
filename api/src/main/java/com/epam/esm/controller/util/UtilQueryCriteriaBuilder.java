package com.epam.esm.controller.util;

import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.exception.ExpectationFailedException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
     * @throws ExpectationFailedException indicates that criterion spelling is incorrect
     */
    public List<QueryCriteria> createSortOrder(String criteria) throws ExpectationFailedException {
        List<QueryCriteria> queryCriterion = new ArrayList<>();
        int separatorPosition;
        if (criteria != null) {
            List<String> sortCriterion = Arrays.stream(criteria.split(",")).collect(Collectors.toList());
            for (String sortCriteria : sortCriterion) {
                separatorPosition = sortCriteria.indexOf("-");
                if (separatorPosition > 0) {
                    queryCriterion.add(new QueryCriteria(
                            sortCriteria.substring(0, separatorPosition),
                            sortCriteria.substring(separatorPosition + 1))
                    );
                } else {
                    throw new ExpectationFailedException(40017);
                }
            }
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