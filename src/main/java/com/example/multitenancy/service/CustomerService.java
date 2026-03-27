package com.example.multitenancy.service;

import com.example.multitenancy.dto.CreateCustomerRequest;
import com.example.multitenancy.entity.Customer;
import com.example.multitenancy.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer create(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + id));
    }

    public List<Customer> searchByName(String name) {
        return customerRepository.findByNameLike(name);
    }

    public Customer findByEmailNative(String email) {
        return customerRepository.findByEmailNative(email)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + email));
    }
}
