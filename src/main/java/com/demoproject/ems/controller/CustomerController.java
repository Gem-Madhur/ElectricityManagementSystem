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

@RestController
@RequestMapping
public class CustomerController {

    /**
     * Autowired customerServiceIm
     */
    @Autowired
    private CustomerServiceIm customerServiceIm;

    /**
     * To get List of all Customers
     * @return ResponseEntity
     */
    @GetMapping(path = "/customers",produces = "application/json")
    public ResponseEntity<List<Customer>> getCustomers() throws DataNotFoundException{
    return new ResponseEntity<>(customerServiceIm.getCustomers(), HttpStatus.OK);
    }

    /**
     * To get Details of Customer with Customer's ID
     * @param cId - Customer's ID
     * @return ResponseEntity
     */
    @GetMapping(path = "/customerById/{cId}",produces = "application/json")
    public ResponseEntity<Customer>getCustomerById(@PathVariable final Long cId) throws IdNotFoundException {
        return new ResponseEntity<>(customerServiceIm.getCustomerById(cId), HttpStatus.OK);
    }

    /**
     * To Add a new Customer
     * @param customer - Customer's details
     * @return ResponseEntity
     */
    @PostMapping(path = "/addCustomer",produces = "application/json",consumes = "application/json")
    public ResponseEntity<Customer> addCustomer(@RequestBody final Customer customer)throws ResourceNotFoundException{
        return new ResponseEntity<>(customerServiceIm.addCustomer(customer),HttpStatus.CREATED);
    }

    /**
     * to Delete a Customer
     * @param cId - Customer's ID
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/delete/{cId}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable final Long cId) throws IdNotFoundException{
        customerServiceIm.deleteCustomer(cId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * to Update Customer Details
     * @param cId - Customer's ID
     * @param customer - Customer's details
     * @return ResponseEntity
     */
    @PutMapping(path = "/update/{cId}" , produces = "application/json",consumes = "application/json")
    public ResponseEntity<Customer> updateCustomer(@PathVariable final Long cId,@RequestBody final Customer customer)throws Exception{
        return new ResponseEntity<>(customerServiceIm.updateCustomerById(cId,customer),HttpStatus.OK);
    }

    /**]
     * to Update Current reading in a bill and to get the Bill Amount
     * @param cId Customer's ID
     * @param currReading - current reading of the bill
     * @return ResponseEntity
=     */
    @PutMapping(produces = "application/json")
    public ResponseEntity<Customer> updateCurrentReading(@RequestParam final Long cId ,@RequestParam final Long currReading) throws Exception{
        Customer customer = customerServiceIm.updateCurrentReading(cId,currReading);
        return new ResponseEntity<>(customer,HttpStatus.OK);
    }

}
