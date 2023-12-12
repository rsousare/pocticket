package com.nttdata.ecommerce.service;

import com.nttdata.ecommerce.entities.Orders;

import java.util.List;

public interface OrdersService {

    List<Orders> findAllOrders();
    Orders getOrderById(Long id);
    Orders createOrder(Orders order);

}
