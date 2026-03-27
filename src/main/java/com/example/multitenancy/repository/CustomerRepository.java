package com.example.multitenancy.repository;

import com.example.multitenancy.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // JPA derived method
    Optional<Customer> findByEmail(String email);

    // JPQL custom query
    @Query("select c from Customer c where lower(c.name) like lower(concat('%', :name, '%'))")
    List<Customer> findByNameLike(@Param("name") String name);

    // Native query
    @Query(value = "select * from customers where email = :email", nativeQuery = true)
    Optional<Customer> findByEmailNative(@Param("email") String email);
}
