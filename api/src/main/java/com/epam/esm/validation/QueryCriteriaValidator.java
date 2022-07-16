package com.epam.esm.validation;

import com.epam.esm.entity.QueryCriteria;
import com.epam.esm.exception.ExpectationFailedException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The validator for QueryCriteria come from URL.
 * Checks if names of all criteria are correct.
 */
@Component
public class QueryCriteriaValidator {
    private static final int EXPECTATION_FAILED_EXCEPTION_CODE = 40017;

    public QueryCriteriaValidator() {
    }

    public void sortCriteriaValidation(List<QueryCriteria> criterion) throws ExpectationFailedException {
        for (QueryCriteria criteria : criterion) {
            if (!(criteria.getValue().equals("asc") || criteria.getValue().equals("desc"))) {
                throw new ExpectationFailedException(EXPECTATION_FAILED_EXCEPTION_CODE);
            }
            if (!(criteria.getField().equals("certificateName")
                    || criteria.getField().equals("createDate"))
                    || criteria.getField().equals("lastUpdateDate")) {
                throw new ExpectationFailedException(EXPECTATION_FAILED_EXCEPTION_CODE);
            }
        }
    }

    public void searchCriteriaValidation(List<QueryCriteria> criterion) throws ExpectationFailedException {
        for (QueryCriteria criteria : criterion) {
            if (!(criteria.getField().equals("certificateName")
                    || criteria.getField().equals("tagName"))
                    || criteria.getField().equals("description")) {
                throw new ExpectationFailedException(EXPECTATION_FAILED_EXCEPTION_CODE);
            }
        }
    }
}