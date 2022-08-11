package com.epam.esm.repository;

import com.epam.esm.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    /**
     * Get order optional by id
     *
     * @param id the order id
     * @return the order optional
     */
    Optional<Order> find(int id);

    /**
     * Add new order
     *
     * @param order the order entity
     */
    void create(Order order);

    /**
     * Get all orders
     *
     * @param pageNumber        the number of page being viewed
     * @param pageElementAmount amount of elements per page
     * @return list of orders
     */
    List<Order> findAll(int pageNumber, int pageElementAmount);

    /**
     * Get amount of all orders in database
     *
     * @return number of all orders in database
     */
    int findAmount();
}
