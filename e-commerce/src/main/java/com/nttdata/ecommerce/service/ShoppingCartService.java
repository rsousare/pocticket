package com.nttdata.ecommerce.service;

import com.nttdata.ecommerce.entities.ShoppingCart;


public interface ShoppingCartService {

    ShoppingCart createShoppingCart(ShoppingCart shoppingCart);
    void calculateTotals(ShoppingCart shoppingCart);
}
