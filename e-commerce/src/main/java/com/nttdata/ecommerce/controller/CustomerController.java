package com.nttdata.ecommerce.controller;

import com.nttdata.ecommerce.dto.CustomerDTO;

import com.nttdata.ecommerce.entities.Customer;

import com.nttdata.ecommerce.mapper.Mapper;
import com.nttdata.ecommerce.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    private Mapper customerMapper;

    @GetMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getCustomers() {
        return customerService.findAllCustomers().stream()
                .map(costumer -> customerMapper.mapToDTO(costumer, CustomerDTO.class))
                .toList();

    }
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCostumerById(@PathVariable("id") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return customerMapper.mapToDTO(customer, CustomerDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable("id") Long id, @RequestBody @Valid CustomerDTO costumerDto) {
        Customer costumerRequest = customerMapper.map(costumerDto, Customer.class);
        Customer costumer = customerService.updateCustomer(costumerRequest, id);
        return customerMapper.mapToDTO(costumer, CustomerDTO.class);
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        Customer postCustomer = customerMapper.map(customerDTO, Customer.class);
        Customer customer = customerService.createCustomer(postCustomer);
        return customerMapper.mapToDTO(customer, CustomerDTO.class);
    }

}
