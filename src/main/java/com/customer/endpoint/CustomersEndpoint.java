package com.customer.endpoint;

import com.customer.model.Address;
import com.customer.model.Customer;
import com.customer.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomersEndpoint {
    private final CustomerService customerService;

    @GetMapping
    @ApiOperation(value = "Retrieve list of customer details")
    @ApiResponses({@ApiResponse(code = 200, message = "Successfully retrieved list")})
    public List<Customer> retrieveCustomers() {
        return customerService.retrieveCustomers();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retrieves a customer details by given customerId")
    @ApiResponses({@ApiResponse(code = 200, message = "Customer found"),
                   @ApiResponse(code = 404, message = "The customer Id you were trying to reach is not found")})
    public Customer retrieveCustomerById(@PathVariable String id) {
        return customerService.retrieveCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Save customer data and returns saved customer details")
    @ApiResponses({@ApiResponse(code = 400, message = "Cannot create customer"),
                   @ApiResponse(code = 201, message = "Created customer data")})
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Update Customer address data and customer details ")
    @ApiResponses({@ApiResponse(code = 400, message = "Cannot update customer"),
                   @ApiResponse(code = 202, message = "Updated customer address data")})
    public Customer updateCustomerAddress(@PathVariable String id, @RequestBody Address address) {
        return customerService.updateCustomerAddress(address, id);
    }

    @GetMapping(params = {"firstName", "lastName"})
    @ApiOperation(value = "Retrieves a customer details by first name And/Or last name")
    @ApiResponses({@ApiResponse(code = 200, message = "Customer found")})
    public List<Customer> retrieveCustomerByName(@RequestParam(required = false) String firstName,
                                                 @RequestParam(required = false) String lastName)
    {
        return customerService.retrieveCustomersByName(firstName, lastName);
    }

}
