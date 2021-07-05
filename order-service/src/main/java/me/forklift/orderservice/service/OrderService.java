package me.forklift.orderservice.service;

import me.forklift.orderservice.dto.OrderDto;
import me.forklift.orderservice.repository.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
