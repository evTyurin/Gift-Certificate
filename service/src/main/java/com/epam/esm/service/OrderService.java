package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;

import java.util.List;

public interface OrderService {

    /**
     * Get all orders dto
     *
     * @param pageNumber        the number of page being viewed
     * @param pageElementAmount amount of elements per page
     * @return list of orders dto
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     */
    List<Order> findAll(int pageNumber, int pageElementAmount) throws
            PageElementAmountException,
            PageNumberException;

    /**
     * Get order dto by id
     *
     * @param id the order id
     * @return the gift certificate dto
     * @throws NotFoundException indicates that order with this id not exist
     */
    Order find(int id) throws NotFoundException;

    /**
     * Add new order
     *
     * @param userId             the id of the user whi made this order
     * @param giftCertificateIds the ids of gift certificates that consist in order
     * @throws NotFoundException indicates that user with this id not exist
     */
    void create(int userId, List<Integer> giftCertificateIds) throws NotFoundException;

    /**
     * Get amount of all orders in database
     *
     * @return number of all orders in database
     */
    int findAmount();
}
