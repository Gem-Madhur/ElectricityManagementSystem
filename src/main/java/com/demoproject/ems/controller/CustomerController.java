package com.demoproject.ems.controller;

import com.demoproject.ems.entity.Customer;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.service.CustomerServiceIm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ye coomit bhi hatna chaiye

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    /**
     * Autowired customerServiceIm.
     */
    @Autowired
    private CustomerServiceIm customerServiceIm;

    /**
     * To get List of all Customers.
     *
     * @return ResponseEntity
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Customer>> getCustomers() throws DataNotFoundException {
        return new ResponseEntity<>(customerServiceIm.getCustomers(), HttpStatus.OK);
    }

    /**
     * To get Details of Customer with Customer's ID.
     *
     * @param customerId - Customer's ID
     * @return ResponseEntity
     */
    @GetMapping(path = "/customerById/{customerId}", produces = "application/json")
    public ResponseEntity<Customer> getCustomerById(@PathVariable final Long customerId) throws IdNotFoundException {
        return new ResponseEntity<>(customerServiceIm.getCustomerById(customerId), HttpStatus.OK);
    }

    /**
     * To Add a new Customer.
     *
     * @param customer - Customer's details
     * @return ResponseEntity
     */
    @PostMapping(path = "/addCustomer", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Customer> addCustomer(@RequestBody final Customer customer) throws ResourceNotFoundException {
        return new ResponseEntity<>(customerServiceIm.addCustomer(customer), HttpStatus.CREATED);
    }

    /**
     * to Delete a Customer.
     *
     * @param customerId - Customer's ID
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable final Long customerId) throws IdNotFoundException {
        customerServiceIm.deleteCustomer(customerId);
        return new ResponseEntity<>("Customer Deleted with ID- " + customerId, HttpStatus.OK);
    }

    /**
     * to Update Customer Details.
     *
     * @param customerId - Customer's ID
     * @param customer   - Customer's details
     * @return ResponseEntity
     */
    @PutMapping(path = "/update/{customerId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Customer> updateCustomer(@PathVariable final Long customerId, @RequestBody final Customer customer) throws Exception {
        return new ResponseEntity<>(customerServiceIm.updateCustomerById(customerId, customer), HttpStatus.OK);
    }

    /**
     * ]
     * to Update Current reading in a bill and to get the Bill Amount.
     *
     * @param customerId  Customer's ID
     * @param currReading - current reading of the bill
     * @return ResponseEntity
     * =
     */
    @PutMapping(produces = "application/json")
    public ResponseEntity<Customer> updateCurrentReading(@RequestParam final Long customerId, @RequestParam final Long currReading) throws Exception {
        Customer customer = customerServiceIm.updateCurrentReading(customerId, currReading);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
