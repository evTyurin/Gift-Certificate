package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.service.constants.ExceptionCode;
import com.epam.esm.service.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class implements UserService interface
 * This class includes methods that process requests from controller,
 * validate them and pass to dao class methods for getting data from database.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Validation validation;

    @Override
    public int findAmount() {
        return userRepository.findAmount();
    }

    @Override
    public User find(int id) throws NotFoundException {
        return userRepository.find(id)
                .orElseThrow(()
                        -> new NotFoundException(id, ExceptionCode.NOT_FOUND_EXCEPTION));
    }

    @Override
    public List<User> findAll(int page, int size) throws PageElementAmountException, PageNumberException {
        validation.pageElementAmountValidation(size);
        int usersAmount = userRepository.findAmount();
        validation.pageAmountValidation(usersAmount, size, page);
        return userRepository.findAll(page, size);
    }
}
