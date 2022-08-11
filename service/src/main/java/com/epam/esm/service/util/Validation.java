package com.epam.esm.service.util;

import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import org.springframework.stereotype.Component;

@Component
public class Validation {

    public void pageElementAmountValidation(int pageElementAmount) throws PageElementAmountException {
        if (pageElementAmount > 100 || pageElementAmount < 0) {
            throw new PageElementAmountException(40017);
        }
    }

    public void pageAmountValidation(int tagsAmount,
                                     int pageElementAmount,
                                     int pageNumber) throws PageNumberException {
        int pageAmount = (int) Math.ceil((double) tagsAmount / pageElementAmount);
        if (pageNumber > pageAmount || pageNumber <= 0) {
            throw new PageNumberException(pageAmount, 40017);
        }
    }


}
