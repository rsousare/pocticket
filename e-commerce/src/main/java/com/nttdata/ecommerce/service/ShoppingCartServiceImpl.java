package com.nttdata.ecommerce.service;


import com.nttdata.ecommerce.entities.ShoppingCart;
import com.nttdata.ecommerce.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCart createShoppingCart(ShoppingCart shoppingCart) {
        return shoppingCartRepository.save(shoppingCart);
    }

    public void calculateTotals(ShoppingCart shoppingCart) {
        double totalPrice = shoppingCart.getQuantity() * shoppingCart.getProduct().getPrice();

        BigDecimal roundedTotal = BigDecimal.valueOf(totalPrice).setScale(2, RoundingMode.HALF_UP);
        shoppingCart.setTotalPrice(roundedTotal.doubleValue());
    }
}
