package com.gmail.merikbest2015.ecommerce.repository;

import com.gmail.merikbest2015.ecommerce.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"perfumes", "user", "user.roles"})
    Page<Order> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"perfumes", "user", "user.roles"})
    Optional<Order> getById(Long orderId);

    @EntityGraph(attributePaths = {"perfumes"})
    Optional<Order> getByIdAndUserId(Long orderId, Long userId);

    @EntityGraph(attributePaths = {"perfumes", "user", "user.roles"})
    Page<Order> findOrderByUserId(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"perfumes", "user", "user.roles"})
    @Query("SELECT orders FROM Order orders WHERE " +
            "(CASE " +
            "   WHEN :searchType = 'firstName' THEN UPPER(orders.firstName) " +
            "   WHEN :searchType = 'lastName' THEN UPPER(orders.lastName) " +
            "   ELSE UPPER(orders.email) " +
            "END) " +
            "LIKE UPPER(CONCAT('%',:text,'%'))")
    Page<Order> searchUsers(String searchType, String text, Pageable pageable);
}
