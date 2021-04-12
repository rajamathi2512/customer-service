package com.customer.reporsitories;

import com.customer.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findByFirstNameOrLastName(String firstName, String lastName, Pageable pageable);
    List<Customer> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);
}
