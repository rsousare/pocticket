package com.nttdata.ecommerce.controller;


import com.nttdata.ecommerce.dto.CustomerDTO;
import com.nttdata.ecommerce.dto.ShoppingCartDTO;
import com.nttdata.ecommerce.dto.OrdersDTO;
import com.nttdata.ecommerce.entities.Orders;
import com.nttdata.ecommerce.mapper.Mapper;
import com.nttdata.ecommerce.service.OrdersService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrdersController {

    private OrdersService ordersService;

    private Mapper ordersMapper;
    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<OrdersDTO> getAllOrders() {
        List<Orders> ordersList = ordersService.findAllOrders();

        return ordersList.stream()
                .map(order -> {
                    List<ShoppingCartDTO> itemDTOList = ordersMapper.mapCartToList(order.getItems(), ShoppingCartDTO.class);
                    OrdersDTO orderDTO = ordersMapper.mapToDTO(order, OrdersDTO.class);
                    orderDTO.setItems(itemDTOList);
                    orderDTO.setCustomer(ordersMapper.mapToDTO(order.getCustomer(), CustomerDTO.class));
                    return orderDTO;
                })
                .toList();
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrdersDTO getOrderById(@PathVariable("id") Long id) {
        Orders order = ordersService.getOrderById(id);
        List<ShoppingCartDTO> itemDTOList = ordersMapper.mapCartToList(order.getItems(), ShoppingCartDTO.class);
        OrdersDTO orderDTO = ordersMapper.mapToDTO(order, OrdersDTO.class);
        orderDTO.setItems(itemDTOList);
        orderDTO.setCustomer(ordersMapper.mapToDTO(order.getCustomer(), CustomerDTO.class));

        return orderDTO;
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public OrdersDTO createOrder(@RequestBody @Valid OrdersDTO ordersDto) {
        Orders postRequest = ordersMapper.mapToEntity(ordersDto, Orders.class);
        postRequest.getItems().forEach(cart -> cart.setOrder(postRequest));
        Orders orders = ordersService.createOrder(postRequest);
        List<ShoppingCartDTO> itemDTOList = ordersMapper.mapCartToList(orders.getItems(), ShoppingCartDTO.class);
        OrdersDTO ordersDTO = ordersMapper.mapToDTO(orders, OrdersDTO.class);
        ordersDTO.setItems(itemDTOList);
        ordersDTO.setCustomer(ordersMapper.mapToDTO(orders.getCustomer(), CustomerDTO.class));

        return ordersDTO;
    }
}

