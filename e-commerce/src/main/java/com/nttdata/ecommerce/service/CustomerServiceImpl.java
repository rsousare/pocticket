package com.nttdata.ecommerce.service;

import com.nttdata.ecommerce.entities.Customer;
import com.nttdata.ecommerce.exceptions.ResourceNotFoundException;
import com.nttdata.ecommerce.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID: " + id + " not found."));
    }

    @Override
    public Customer updateCustomer(Customer customer, Long id) {
        Customer existingCostumer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer with ID: " + id + " not found."));
        existingCostumer.setName(customer.getName());
    existingCostumer.setEmail(customer.getEmail());
    existingCostumer.setCity(customer.getCity());

        return customerRepository.saveAndFlush(existingCostumer);
    }

    @Override
    public Customer createCustomer(Customer costumer) {
        return customerRepository.save(costumer);
    }

}
