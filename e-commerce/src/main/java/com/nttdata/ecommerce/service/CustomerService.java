package com.nttdata.ecommerce.service;

import com.nttdata.ecommerce.entities.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomers();
    Customer getCustomerById(Long id);
    Customer updateCustomer(Customer customer, Long id);
    Customer createCustomer(Customer customer);

}
