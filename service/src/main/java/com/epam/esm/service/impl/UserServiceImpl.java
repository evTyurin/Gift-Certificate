package com.epam.esm.service.impl;

import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.ExpectationFailedException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.NotFoundLoginException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.entity.security.JwtUser;
import com.epam.esm.service.UserService;
import com.epam.esm.service.constants.ExceptionCode;
import com.epam.esm.service.util.Validation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class implements UserService interface
 * This class includes methods that process requests from controller,
 * validate them and pass to dao class methods for getting data from database.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Validation validation;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           Validation validation,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.validation = validation;
        this.passwordEncoder = passwordEncoder;
    }

//    @Override
//    public int findAmount() {
//        return userRepository.findAmount();
//    }
//
//    @Override
//    public User find(int id) throws NotFoundException {
//        return userRepository.find(id)
//                .orElseThrow(()
//                        -> new NotFoundException(id, ExceptionCode.NOT_FOUND_EXCEPTION));
//    }

    @Override
    public List<User> findAll(int page, int size) throws PageElementAmountException, PageNumberException {
        validation.pageElementAmountValidation(size);
//        int usersAmount = userRepository.findAmount();
        int usersAmount = (int)userRepository.count();
        validation.pageAmountValidation(usersAmount, size, page);
        Pageable paging = PageRequest.of(page, size);
//        return userRepository.findAll(page, size);
        return userRepository.findAll(paging).toList();
    }
//
//    @Override
//    public User find(String login, String password) throws NotFoundLoginException {
//        User userEntity = find(login);
//        if (userEntity != null && passwordEncoder.matches(password, userEntity.getPassword())) {
//                return userEntity;
//        }
//        return null;
//    }

//    @Override
//    public User find(String login) throws NotFoundLoginException {
//        return userRepository.find(login).orElseThrow(()
//                -> new NotFoundLoginException(login, ExceptionCode.NOT_FOUND_EXCEPTION));
//    }

    @Override
    public User find(String login) throws NotFoundLoginException {
        //Optional<User> user = userRepository.findByLogin(login);
        return userRepository.findByLogin(login).orElseThrow(()
                -> new NotFoundLoginException(login, ExceptionCode.NOT_FOUND_EXCEPTION));
    }

//    @Override
//    public void create(User user) throws ExpectationFailedException, NotFoundLoginException, EntityExistException {
//        if(user.getLogin().isEmpty() || user.getPassword().isEmpty()) {
//            throw new ExpectationFailedException(40000);
//        }
//        User existUser = find(user.getLogin());
//        if(existUser != null) {
//            throw new EntityExistException(existUser.getId(), ExceptionCode.ENTITY_EXIST_EXCEPTION);
//        }
//        userRepository.create(user);
//    }

//    @Override
//    public UserDetails map(User user) {
//        return new JwtUser(
//                user.getId(),
//                user.getName(),
//                user.getPassword(),
//                map(user.getRoles())
//        );
//    }
//
//    private Collection<? extends GrantedAuthority> map(List<Role> userRole) {
//        return userRole
//                .stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
//    }
}
