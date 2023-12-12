package com.nttdata.ecommerce.entities;

import com.nttdata.ecommerce.utils.ShoppingCartId;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@IdClass(ShoppingCartId.class)
public class ShoppingCart {

    @Id
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Id
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private Double totalPrice;

}
