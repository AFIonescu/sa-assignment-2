package com.sa.ecommerce.repository;

import com.sa.ecommerce.model.Order;
import com.sa.ecommerce.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByCustomerEmail(String customerEmail);

    List<Order> findByCustomerName(String customerName);
}
