package com.epam.esm.builder;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderBuilder {
    private final UserBuilder userBuilder;
    private final GiftCertificateBuilder giftCertificateBuilder;

    public OrderBuilder(UserBuilder userBuilder, GiftCertificateBuilder giftCertificateBuilder) {
        this.userBuilder = userBuilder;
        this.giftCertificateBuilder = giftCertificateBuilder;
    }

    public Order build(OrderDto orderDto) {
        return Order
                .builder()
                .price(orderDto.getPrice())
                .purchaseDate(orderDto.getPurchaseDate())
                .user(userBuilder.build(orderDto.getUser()))
                .build();
    }

    public OrderDto build(Order order) {
        return OrderDto
                .builder()
                .id(order.getId())
                .price(order.getPrice())
                .purchaseDate(order.getPurchaseDate())
                .user(userBuilder.build(order.getUser()))
                .giftCertificates(order.getCertificates()
                        .stream()
                        .map(giftCertificateBuilder::build)
                        .collect(Collectors.toList()))
                .build();
    }
}
