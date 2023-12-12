package com.nttdata.ecommerce.repository;

import com.nttdata.ecommerce.entities.ShoppingCart;
import com.nttdata.ecommerce.utils.ShoppingCartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, ShoppingCartId> {

}

