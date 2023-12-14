package com.nttdata.ecommerce.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrdersDTO {

    private Long id;
    private String orderNumber;
    private Date creationDate;
    private Date deliveryDate;
    private CustomerDTO customer;
    private List<ShoppingCartDTO> items;
    private Double grandTotal;
}
