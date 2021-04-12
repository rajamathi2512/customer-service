package com.customer.endpoint;

import com.customer.exception.CustomerNotFoundException;
import com.customer.model.Customer;
import com.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CustomersEndpoint.class)
public class CustomersEndpointTest {
    @MockBean
    CustomerService customerService;
    @Autowired
    MockMvc mockMvc;
    private Customer customer;
    private static final String CUSTOMER_ENDPOINT = "/customers/";
    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() {
        customer = Customer.builder().firstName("testName").lastName("TestLastName").age("23").build();
    }

    @Test
    public void testRetrieveCustomers() throws Exception {
        when(customerService.retrieveCustomers()).thenReturn(Collections.singletonList(customer));
        mockMvc.perform(get(CUSTOMER_ENDPOINT).content(CONTENT_TYPE).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    public void testRetrieveCustomerById() throws Exception {
        when(customerService.retrieveCustomerById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27")).thenReturn(customer);
        mockMvc.perform(get(CUSTOMER_ENDPOINT + "/cc42cd8d-009a-4dfd-9a71-6a0ea8202a27").content(CONTENT_TYPE)
                                                                                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    public void testRetrieveCustomerById_Throw_Exception() throws Exception {
        when(customerService.retrieveCustomerById("cc42cd8d-009a-4dfd-9a71-6a0ea8202a27")).thenThrow(new CustomerNotFoundException(
                "cc42cd8d-009a-4dfd-9a71-6a0ea8202a24"));
        mockMvc.perform(get(CUSTOMER_ENDPOINT + "/cc42cd8d-009a-4dfd-9a71-6a0ea8202a27").content(CONTENT_TYPE)
                                                                                        .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCustomer() throws Exception {
        when(customerService.createCustomer(customer)).thenReturn(customer);
        mockMvc.perform(post(CUSTOMER_ENDPOINT).content(CONTENT_TYPE)
                                               .content(objectMapper.writeValueAsString(customer))
                                               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateCustomerAddress() throws Exception {
        when(customerService.createCustomer(customer)).thenReturn(customer);
        mockMvc.perform(put(CUSTOMER_ENDPOINT+"/cc42cd8d-009a-4dfd-9a71-6a0ea8202a27").content(CONTENT_TYPE)
                                               .content(objectMapper.writeValueAsString(customer))
                                               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isAccepted());
    }
    @Test
    public void testRetrieveCustomerByName() throws Exception {
        when(customerService.createCustomer(customer)).thenReturn(customer);
        mockMvc.perform(get(CUSTOMER_ENDPOINT+"search?firstName=Test&lastName=name").content(CONTENT_TYPE)
                                                                                       .content(objectMapper.writeValueAsString(customer))
                                                                                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }
}
