package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    /**
     * Find user by id
     *
     * @param id the user id
     * @return the user entity
     */
    Optional<User> find(int id);

    /**
     * Get all users dto
     *
     * @param pageNumber        the number of page being viewed
     * @param pageElementAmount amount of elements per page
     * @return list of users dto
     */
    List<User> findAll(int pageNumber, int pageElementAmount);

    /**
     * Get amount of all tags in database
     *
     * @return number of all tags in database
     */
    int findAmount();
}
