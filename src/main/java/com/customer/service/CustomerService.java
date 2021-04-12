package com.customer.service;

import com.customer.exception.CustomerNotFoundException;
import com.customer.model.Address;
import com.customer.model.Customer;
import com.customer.reporsitories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    @Value("${customer.search.limit}")
    public String CUSTOMER_SEARCH_LIMIT;

    public List<Customer> retrieveCustomers() {
        return customerRepository.findAll();
    }

    public Customer retrieveCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomerAddress(Address address, String id) {
        return customerRepository.findById(id).map(customer -> buildCustomerAddressDetails(customer, address)).
                map(customerRepository::save).orElse(null);
    }

    public List<Customer> retrieveCustomersByName(String firstName, String lastName) {
        if(Strings.isNotEmpty(firstName) && Strings.isNotEmpty(lastName)){
            return new ArrayList<>(customerRepository.findByFirstNameAndLastName(firstName,
                                                                                lastName,
                                                                                PageRequest.of(0,
                                                                                               Integer.parseInt(
                                                                                                       CUSTOMER_SEARCH_LIMIT))));
        }
        return new ArrayList<>(customerRepository.findByFirstNameOrLastName(firstName,
                                                                            lastName,
                                                                            PageRequest.of(0,
                                                                                           Integer.parseInt(
                                                                                                   CUSTOMER_SEARCH_LIMIT))));
    }

    private Customer buildCustomerAddressDetails(Customer customer, Address address) {
        customer.setCurrentAddress(address);
        return customer;
    }

}
