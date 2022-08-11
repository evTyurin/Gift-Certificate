package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.constants.ExceptionCode;
import com.epam.esm.service.util.Validation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;
    @Mock
    private Validation validation;

    @AfterEach
    void tearDown() {
        Mockito.verifyNoMoreInteractions(userRepository,
                giftCertificateRepository,
                validation);
    }

    @Test
    void find_findOrderByCorrectId_findOrder() throws NotFoundException {
        Order expectedOrder = new Order();
        expectedOrder.setId(1);

        when(orderRepository.find(1)).thenReturn(Optional.of(Order.builder().id(1).build()));

        Order actualOrder = orderService.find(1);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void find_findUserByIncorrectId_throwException() {
        Order expectedOrder = new Order();
        expectedOrder.setId(1);

        when(orderRepository.find(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.find(1));
    }

    @Test
    void find_findOrdersAmount_returnNumberOfAllUsers() {
        orderService.findAmount();
        verify(orderRepository).findAmount();
    }

    @Test
    void findAll_findAllUsersWithCorrectPagination_findUsers() throws PageElementAmountException, PageNumberException {
        Order expectedOrder = new Order();
        expectedOrder.setId(1);
        Order anotherOrder = new Order();
        anotherOrder.setId(2);
        List<Order> expectedOrders = new ArrayList<>();
        expectedOrders.add(expectedOrder);
        expectedOrders.add(anotherOrder);

        doNothing().when(validation).pageAmountValidation(anyInt(), anyInt(), anyInt());
        doNothing().when(validation).pageElementAmountValidation(anyInt());

        when(orderRepository.findAmount()).thenReturn(10);
        when(orderRepository.findAll(1, 2)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.findAll(1, 2);

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    void create_createOrder_createSuccessfully() throws NotFoundException {
        GiftCertificate expectedGiftCertificate = new GiftCertificate();
        expectedGiftCertificate.setId(1);
        expectedGiftCertificate.setPrice(100.0);
        GiftCertificate anotherGiftCertificate = new GiftCertificate();
        anotherGiftCertificate.setId(2);
        anotherGiftCertificate.setPrice(200.0);

        when(userRepository.find(1)).thenReturn(Optional.of(User.builder().id(1).build()));
        when(giftCertificateRepository.find(1)).thenReturn(Optional.of(expectedGiftCertificate));
        when(giftCertificateRepository.find(2)).thenReturn(Optional.of(anotherGiftCertificate));

        User expectedUser = userRepository.find(1).orElseThrow(()
                -> new NotFoundException(1, ExceptionCode.NOT_FOUND_EXCEPTION));
        GiftCertificate actualGiftCertificate = giftCertificateRepository.find(1).orElseThrow(() -> new NotFoundException(1, ExceptionCode.NOT_FOUND_EXCEPTION));
        GiftCertificate anotherActualGiftCertificate = giftCertificateRepository.find(2).orElseThrow(() -> new NotFoundException(1, ExceptionCode.NOT_FOUND_EXCEPTION));
        List<GiftCertificate> giftCertificates = new ArrayList<>();
        giftCertificates.add(actualGiftCertificate);
        giftCertificates.add(anotherActualGiftCertificate);
        double totalCost = giftCertificates.stream().mapToDouble(GiftCertificate::getPrice).sum();
        Order order = Order.builder().certificates(giftCertificates).user(expectedUser).price(totalCost).purchaseDate(LocalDateTime.of(2021, 5, 4, 12, 12, 12)).build();
        orderRepository.create(order);
        orderService.create(1, new ArrayList<>(
                Arrays.asList(1, 2)));

        verify(userRepository, times(2)).find(1);
        verify(giftCertificateRepository, times(2)).find(1);
        verify(giftCertificateRepository, times(2)).find(2);
        verify(orderRepository).create(order);
    }
}