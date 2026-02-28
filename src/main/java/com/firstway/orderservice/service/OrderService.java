package com.firstway.orderservice.service;

import com.firstway.orderservice.event.OrderCreatedEvent;
import com.firstway.orderservice.messaging.OrderEventProducer;
import com.firstway.orderservice.model.Order;
import com.firstway.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final OrderEventProducer eventProducer;
    public OrderService(OrderRepository orderRepository, OrderEventProducer eventProducer) {
        this.orderRepository = orderRepository;
        this.eventProducer = eventProducer;
    }

    @Transactional
    public Order createOrder(Order order) {
        log.info("Creating order for customer: {}", order.getCustomerName());

        if (order.getTotalPrice() == null) {
            order.setTotalPrice(order.getQuantity() * 10.0);
        }

        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerName(),
                savedOrder.getProductName(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice()
        );
        eventProducer.sendOrderCreatedEvent(event);
        log.info("Order created successfully with ID: {}", savedOrder.getId());

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));
    }

    @Transactional
    public Order updateOrderStatus(Long id, String newStatus) {
        log.info("Updating order {} status to: {}", id, newStatus);
        Order order = getOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByStatus(String status) {
        log.info("Fetching orders with status: {}", status);
        return orderRepository.findByStatus(status);
    }
}