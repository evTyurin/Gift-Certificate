package com.epam.esm.controller;

import com.epam.esm.builder.OrderBuilder;
import com.epam.esm.controller.constants.ControllerConstants;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PurchaseOrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.hateoas.HateoasEntity;
import com.epam.esm.hateoas.PaginationHateoas;
import com.epam.esm.service.OrderService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * RestController that handles requests to /orders url
 * Contains operations with orders
 */
@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderBuilder orderBuilder;
    private final HateoasEntity hateoasEntity;
    private final PaginationHateoas paginationHateoas;

    public OrderController(OrderService orderService,
                           OrderBuilder orderBuilder,
                           HateoasEntity hateoasEntity,
                           PaginationHateoas paginationHateoas) {
        this.orderService = orderService;
        this.orderBuilder = orderBuilder;
        this.hateoasEntity = hateoasEntity;
        this.paginationHateoas = paginationHateoas;
    }

    /**
     * Get order dto by id
     *
     * @param id the order id
     * @return the order dto
     * @throws NotFoundException indicates that order with this id not exist
     */
    @GetMapping("{id}")
    public OrderDto find(@PathVariable int id) throws NotFoundException, EntityExistException {
        return hateoasEntity.addOrderLinks(orderBuilder.build(orderService.find(id)));
    }

    /**
     * Get all orders dto
     *
     * @param page the number of page being viewed. It equals 1 by default
     * @param size amount of elements per page. It equals 3 by default
     * @return list of orders dto
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     * @throws NotFoundException          indicates that order with this id not exist
     * @throws EntityExistException       indicates that order with this name already exist
     */
    @GetMapping
    public CollectionModel<OrderDto> findAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                             @RequestParam(name = "size", defaultValue = ControllerConstants.SIZE) int size) throws
            PageElementAmountException,
            PageNumberException,
            NotFoundException,
            EntityExistException {
        List<Order> orders = orderService.findAll(page, size);
        List<OrderDto> ordersDto = new ArrayList<>();
        for (Order order:orders) {
            ordersDto.add(hateoasEntity.addOrderLinks(orderBuilder.build(order)));
        }
        List<Link> links = paginationHateoas.addPaginationOrderLinks(
                size,
                page,
                orderService.findAmount());
        return CollectionModel.of(ordersDto, links);
    }

    /**
     * Add new order
     *
     * @param order the purchase order dto
     * @throws NotFoundException indicates that order with this id not exist
     */
    @PostMapping
    public void create(@Valid @RequestBody PurchaseOrderDto order) throws NotFoundException {
        orderService.create(order.getUserId(), order.getGiftCertificateIds());
    }
}
