package com.customer.service;

import com.customer.exception.CustomerNotFoundException;
import com.customer.model.Customer;
import com.customer.reporsitories.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = CustomerService.class)
@TestPropertySource(locations = "classpath:application.properties")
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;
    @MockBean
    CustomerRepository customerRepository;
    private Customer customer;

    @Before
    public void setup() {
        customer = Customer.builder().firstName("testName").lastName("TestLastName").age("23").build();
    }

    @Test
    public void testRetrieveCustomers() {
        when(customerRepository.findAll()).thenReturn(Collections.singletonList(customer));
        assertEquals(Collections.singletonList(customer), customerService.retrieveCustomers());
        assertTrue(customerService.retrieveCustomers().size() > 0);
    }

    @Test
    public void testRetrieveCustomerById() {
        when(customerRepository.findById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27")).thenReturn(Optional.ofNullable(
                customer));
        assertEquals(customer, customerService.retrieveCustomerById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27"));
        assertEquals("testName",
                     customerService.retrieveCustomerById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27").getFirstName());
        assertEquals("TestLastName",
                     customerService.retrieveCustomerById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27").getLastName());

    }

    @Test(expected = CustomerNotFoundException.class)
    public void testRetrieveCustomerById_Throw_Exception() {
        when(customerRepository.findById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27")).thenThrow(new CustomerNotFoundException(
                "cc42cd8d-009a-4dfd-9a71-6a0ea8202a27"));
        customerService.retrieveCustomerById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27");
    }

    @Test
    public void testCreateCustomer() {
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer, customerService.createCustomer(customer));
    }

    @Test
    public void testUpdateCustomerAddress() {
        when(customerRepository.findById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27")).thenReturn(Optional.ofNullable(
                customer));
        when(customerRepository.save(customer)).thenReturn(customer);
        assertEquals(customer,
                     customerService.updateCustomerAddress(customer.getCurrentAddress(),
                                                           "cc42cd8d-009a-4dfd-9a71-6a0ea8202a27"));
    }
}


