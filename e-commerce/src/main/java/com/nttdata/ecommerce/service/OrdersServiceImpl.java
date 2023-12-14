package com.nttdata.ecommerce.service;


import com.nttdata.ecommerce.entities.Customer;
import com.nttdata.ecommerce.entities.ShoppingCart;
import com.nttdata.ecommerce.entities.Orders;
import com.nttdata.ecommerce.entities.Product;
import com.nttdata.ecommerce.exceptions.ResourceNotFoundException;
import com.nttdata.ecommerce.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private OrdersRepository orderRepository;

    private ShoppingCartService shoppingCartService;

    private CustomerService customerService;

    private ProductService productService;
    @Override
    public List<Orders> findAllOrders() {
        List<Orders> ordersList = orderRepository.findAll();

        if (ordersList.isEmpty()) {
            throw new ResourceNotFoundException("No orders found.");
        }
        return ordersList;
    }
    @Override
    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID: " + id + " not found."));
    }
    @Override
    public Orders createOrder(Orders order) {
        Customer customer = order.getCustomer();
        if (customer != null && customer.getId() != null) {
            customer = customerService.getCustomerById(customer.getId());
            order.setCustomer(customer);
        }
        order.getItems().forEach(cart -> {
            cart.setOrder(order);
            if (cart.getProduct().getPrice() == null || cart.getProduct().getName() == null) {
                Product product = productService.getProductById(cart.getProduct().getId());
                cart.getProduct().setPrice(product.getPrice());
                cart.getProduct().setName(product.getName());
            }
            shoppingCartService.calculateTotals(cart);
        });
        generateOrderNumberAndDates(order);
        calculateGrandTotal(order);
        Orders createdOrder = orderRepository.save(order);
        order.getItems().forEach(shoppingCartService::createShoppingCart);

        return createdOrder;
    }
    private void generateOrderNumberAndDates(Orders order) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        order.setOrderNumber(dateFormat.format(new Date()));
        order.setCreationDate(new Date());
        order.setDeliveryDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
    }
    private void calculateGrandTotal(Orders order) {
        double grandTotal = order.getItems().stream()
                .mapToDouble(ShoppingCart::getTotalPrice)
                .sum();

        BigDecimal roundedTotal = BigDecimal.valueOf(grandTotal).setScale(2, RoundingMode.HALF_DOWN);
        order.setGrandTotal(roundedTotal.doubleValue());
    }
}








