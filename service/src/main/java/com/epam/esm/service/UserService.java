package com.epam.esm.service;

import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.ExpectationFailedException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.NotFoundLoginException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    /**
     * Find user by id
     *
     * @param id the user id
     * @return the user entity
     * @throws NotFoundException indicates that user with this id not exist
     */
    User find(int id) throws NotFoundException;

    /**
     * Get all users dto
     *
     * @param pageNumber        the number of page being viewed
     * @param pageElementAmount amount of elements per page
     * @return list of users dto
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     */
    List<User> findAll(int pageNumber, int pageElementAmount) throws
            PageElementAmountException,
            PageNumberException;

    /**
     * Get amount of all tags in database
     *
     * @return number of all tags in database
     */
    int findAmount();

    void create(User user) throws ExpectationFailedException, NotFoundLoginException, EntityExistException;

    User find(String login, String password) throws NotFoundException, NotFoundLoginException;

    User find(String login) throws NotFoundException, NotFoundLoginException;

    UserDetails map(User user);
}
