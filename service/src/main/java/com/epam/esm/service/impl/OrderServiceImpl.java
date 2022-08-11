package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.entity.Order;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.constants.ExceptionCode;
import com.epam.esm.service.util.Validation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements OrderService interface
 * This class includes methods that process requests from controller,
 * validate them and pass to dao class methods for injecting to database.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository giftCertificateRepository;
    private final Validation validation;

    public OrderServiceImpl(GiftCertificateRepository giftCertificateRepository,
                            UserRepository userRepository,
                            OrderRepository orderRepository,
                            Validation validation) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.validation = validation;
    }

    @Override
    public int findAmount() {
        return orderRepository.findAmount();
    }

    @Override
    public Order find(int id) throws NotFoundException {
        return orderRepository.find(id)
                .orElseThrow(()
                        -> new NotFoundException(id, ExceptionCode.NOT_FOUND_EXCEPTION));
    }

    @Transactional
    @Override
    public void create(int userId, List<Integer> giftCertificateIds) throws NotFoundException {
        User user = userRepository.find(userId)
                .orElseThrow(()
                        -> new NotFoundException(userId, ExceptionCode.NOT_FOUND_EXCEPTION));

        List<GiftCertificate> giftCertificates = new ArrayList<>();
        for (Integer id : giftCertificateIds) {
            giftCertificates.add(giftCertificateRepository.find(id).orElseThrow(()
                    -> new NotFoundException(id, ExceptionCode.NOT_FOUND_EXCEPTION)));
        }
        double totalCost = giftCertificates.stream().mapToDouble(GiftCertificate::getPrice).sum();
        orderRepository.create(Order
                .builder()
                .certificates(giftCertificates)
                .user(user)
                .price(totalCost)
                .purchaseDate(LocalDateTime.now())
                .build());
    }

    @Override
    public List<Order> findAll(int page, int size) throws PageElementAmountException, PageNumberException {
        validation.pageElementAmountValidation(size);
        int ordersAmount = orderRepository.findAmount();
        validation.pageAmountValidation(ordersAmount, size, page);
        return orderRepository.findAll(page, size);
    }
}
