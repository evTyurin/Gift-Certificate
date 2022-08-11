package com.epam.esm.controller;

import com.epam.esm.builder.UserBuilder;
import com.epam.esm.controller.constants.ControllerConstants;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityExistException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.exception.PageElementAmountException;
import com.epam.esm.exception.PageNumberException;
import com.epam.esm.hateoas.HateoasEntity;
import com.epam.esm.hateoas.PaginationHateoas;
import com.epam.esm.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * RestController that handles requests to /users url
 * Contains operations with users
 */
@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final UserBuilder userBuilder;
    private final HateoasEntity hateoasEntity;
    private final PaginationHateoas paginationHateoas;

    public UserController(UserService userService,
                          UserBuilder userBuilder,
                          HateoasEntity hateoasEntity,
                          PaginationHateoas paginationHateoas) {
        this.userService = userService;
        this.userBuilder = userBuilder;
        this.hateoasEntity = hateoasEntity;
        this.paginationHateoas = paginationHateoas;
    }

    /**
     * Get user dto by id
     *
     * @param id the user id
     * @return the user dto
     * @throws NotFoundException indicates that user with this id not exist
     */
    @GetMapping("{id}")
    public UserDto find(@PathVariable int id) throws NotFoundException {
        return hateoasEntity.addUserLinks(userBuilder.build(userService.find(id)));
    }

    /**
     * Get list of all users
     *
     * @param page the number of page being viewed. It equals 1 by default
     * @param size amount of elements per page. It equals 3 by default
     * @return list of users dto
     * @throws NotFoundException          indicates that user with this id not exist
     * @throws EntityExistException       indicates that user with this name already exist
     * @throws PageElementAmountException indicates that amount of elements per page
     *                                    are incorrect (less then 1 or bigger then 100)
     * @throws PageNumberException        indicates that number of page being viewed bigger then amount of pages
     *                                    found by criterion or less then 1
     */
    @GetMapping
    public CollectionModel<UserDto> findAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                            @RequestParam(name = "size", defaultValue = ControllerConstants.SIZE) int size) throws
            PageElementAmountException,
            PageNumberException,
            NotFoundException,
            EntityExistException {
        List<User> users = userService.findAll(page, size);
        List<UserDto> usersDto = new ArrayList<>();
        for (User user : users) {
            usersDto.add(hateoasEntity.addUserLinks(userBuilder.build(user)));
        }
        List<Link> links = paginationHateoas.addPaginationUserLinks(
                size,
                page,
                userService.findAmount());
        return CollectionModel.of(usersDto, links);
    }
}
