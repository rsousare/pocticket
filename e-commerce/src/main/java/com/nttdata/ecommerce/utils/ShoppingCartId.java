package com.nttdata.ecommerce.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class ShoppingCartId implements Serializable {

    private Long order;
    private Long product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCartId that = (ShoppingCartId) o;
        return Objects.equals(order, that.order) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product);
    }
}
