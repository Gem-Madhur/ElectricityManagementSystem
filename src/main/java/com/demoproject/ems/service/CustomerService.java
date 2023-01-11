package com.demoproject.ems.service;

import com.demoproject.ems.entity.Customer;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;

import java.util.List;

public interface CustomerService {
    List<Customer> getCustomers () throws DataNotFoundException;

    Customer getCustomerById(Long cId) throws IdNotFoundException;

    Customer addCustomer(Customer customer) throws ResourceNotFoundException;

    void deleteCustomer(Long cId) throws IdNotFoundException;

    Customer updateCustomerById(Long cId, Customer customer) throws Exception;

    Customer updateCurrentReading(Long cId, Long currReading) throws Exception;
}
