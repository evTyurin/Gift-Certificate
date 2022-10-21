package com.epam.esm.repository;

import com.epam.esm.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    /**
     * Find user by id
     *
     * @param id the user id
     * @return the user entity
     */
//    Optional<User> find(int id);

//    Optional<User> findById(int id);



    /**
     * Get all users dto
     *
//    * @param pageNumber        the number of page being viewed
//     * @param pageElementAmount amount of elements per page
     * @return list of users dto
     */
//    List<User> findAll(int pageNumber, int pageElementAmount);
    Page<User> findAll(Pageable pageable);

    /**
     * Get amount of all tags in database
     *
     * @return number of all tags in database
     */
//    @Query("select count(user) from User user")
//    int findAmount();

    long count();


    Optional<User> findByLogin(String login);

//    void create(User user);
}
