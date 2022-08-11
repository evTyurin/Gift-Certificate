package com.epam.esm.service.impl;

import com.epam.esm.entity.User;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.util.Validation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Validation validation;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(userRepository, validation);
    }

    @Test
    void find_findUserByCorrectId_findUser() throws NotFoundException {
        User expectedUser = new User();
        expectedUser.setId(1);

        when(userRepository.find(1)).thenReturn(Optional.of(User.builder().id(1).build()));

        User actualUser = userService.find(1);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void find_findUserByIncorrectId_throwException() {
        User expectedUser = new User();
        expectedUser.setId(1);

        when(userRepository.find(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.find(1));
    }

    @Test
    void find_findAllUserAmount_returnNumberOfAllUsers() {
        userService.findAmount();
        verify(userRepository).findAmount();
    }

    @Test
    void findAll_findAllUsersWithCorrectPagination_findUsers() throws PageElementAmountException, PageNumberException {
        User expectedUser = new User();
        expectedUser.setName("user1");
        User anotherUser = new User();
        anotherUser.setName("user2");
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(expectedUser);
        expectedUsers.add(anotherUser);

        doNothing().when(validation).pageAmountValidation(anyInt(), anyInt(), anyInt());
        doNothing().when(validation).pageElementAmountValidation(anyInt());

        when(userRepository.findAmount()).thenReturn(10);
        when(userRepository.findAll(1, 2)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.findAll(1, 2);
        assertEquals(expectedUsers, actualUsers);
    }
}
