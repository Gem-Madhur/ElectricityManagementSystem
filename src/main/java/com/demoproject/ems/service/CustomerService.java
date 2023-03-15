package com.demoproject.ems.service;

import com.demoproject.ems.entity.Customer;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers() throws DataNotFoundException;

    Customer getCustomerById(Long customerId) throws IdNotFoundException;

    Customer addCustomer(Customer customer) throws ResourceNotFoundException;

    void deleteCustomer(Long customerId) throws IdNotFoundException;

    Customer updateCustomerById(Long customerId, Customer customer) throws Exception;

    Customer updateCurrentReading(Long customerId, Long currReading) throws Exception;
}
