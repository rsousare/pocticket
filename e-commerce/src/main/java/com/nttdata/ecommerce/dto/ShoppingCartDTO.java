package com.nttdata.ecommerce.dto;
import lombok.Data;
@Data
public class ShoppingCartDTO {

    private int quantity;
    private ProductDTO product;
    private Double totalPrice;

}
