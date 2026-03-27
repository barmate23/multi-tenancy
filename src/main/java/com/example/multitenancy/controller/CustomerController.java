package com.example.multitenancy.controller;

import com.example.multitenancy.dto.CreateCustomerRequest;
import com.example.multitenancy.entity.Customer;
import com.example.multitenancy.service.CustomerService;
import com.example.multitenancy.tenant.TenantContext;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer create(@Valid @RequestBody CreateCustomerRequest request) {
        return customerService.create(request);
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @GetMapping("/search")
    public List<Customer> searchByName(@RequestParam String name) {
        return customerService.searchByName(name);
    }

    @GetMapping("/native")
    public Customer findByEmailNative(@RequestParam String email) {
        return customerService.findByEmailNative(email);
    }

    @GetMapping("/tenant")
    public Map<String, String> currentTenant() {
        return Map.of("schema", TenantContext.getTenantId());
    }
}
