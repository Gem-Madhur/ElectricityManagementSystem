package com.demoproject.ems.service;

import com.demoproject.ems.entity.Customer;
import com.demoproject.ems.entity.Meter;
import com.demoproject.ems.entity.Supplier;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.InvalidReadingException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerServiceIm implements CustomerService {

    /**
     * Autowired customerRepository
     */
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * To get List of all Customers
     * @return List of Customers
]     */
    @Override
    public List<Customer> getCustomers() throws DataNotFoundException{
        List<Customer> customerList = customerRepository.findAll();
        if(customerList.size()== 0){
            log.info("Customer List is Empty.");
            throw new DataNotFoundException("Please enter Customers in the list");
        }
        log.info(customerList.size() +" total customers found");
        return customerList;
    }

    /**
     * To get Details of Customer with Customer's ID
     * @param cId - Customer's ID
     * @return Customer Details
     */
    @Override
    public Customer getCustomerById(final Long cId) throws IdNotFoundException{
        Optional<Customer> customer = customerRepository.findById(cId);
        if(customer.isEmpty()){
            log.info("Invalid Customer Id");
            throw new IdNotFoundException("Customer "+cId+" not found");
        }
        log.info("Customer found with id-" +cId);
        return customer.get();
    }

    /**
     * To Add a new Customer
     * @param customer - Customer's details
     * @return Customer's Details
     */
    @Override
    public Customer addCustomer(final Customer customer) throws ResourceNotFoundException{
        Optional<Meter> meterOptional = Optional.ofNullable(customer.getMeter());
        Optional<Supplier> supplierOptional = Optional.ofNullable(customer.getSupplier());
        if(meterOptional.isEmpty() && supplierOptional.isEmpty()){
            log.info("Invalid or Details are Missing");
            throw new ResourceNotFoundException("Please enter valid Meter and Supplier details");
        }else if(supplierOptional.isEmpty()){
            log.info("invalid or Supplier Details are Missing");
            throw new ResourceNotFoundException("please enter valid Supplier Details");
        }else if(meterOptional.isEmpty()){
            log.info("Invalid or Meter Details are missing");
            throw new ResourceNotFoundException("Please enter valid Meter Details");
        }
        log.info("Customer has been Added");
        customerRepository.save(customer);
        return customer;
    }

    /**
     * to Delete a Customer
     * @param cId - Customer's ID
     */
    @Override
    public void deleteCustomer(final Long cId) throws IdNotFoundException{
        Optional<Customer> customerOptional = customerRepository.findById(cId);
        if(customerOptional.isEmpty()) {
            log.info("invalid Customer ID");
            throw new IdNotFoundException("Customer not found with id-" + cId);
        }
        log.info("Customer with ID: " + cId + " is deleted");
        customerRepository.deleteById(cId);
    }

    /**
     * to Update Customer Details
     * @param cId - Customer's ID
     * @param customer - Customer's details
     * @return Customer's Details
     */
    @Override
    public Customer updateCustomerById(final Long cId,final Customer customer) throws Exception{
        Optional<Customer> customerOptional = customerRepository.findById(cId);
        if(customerOptional.isEmpty()){
            log.info("Invalid Customer Id");
            throw new IdNotFoundException("Customer with id- " + cId+ "not found");
        }
        log.info("Customer found , updating the details");
        Customer existingCustomer = customerOptional.get();
        existingCustomer.setCName(customer.getCName()== null ? existingCustomer.getCName() : customer.getCName());
        existingCustomer.setCAddress(customer.getCAddress()== null ? existingCustomer.getCAddress() : customer.getCAddress());
        existingCustomer.setLastReading(customer.getLastReading()== null ? existingCustomer.getLastReading() : customer.getLastReading());
        existingCustomer.setMeter(customer.getMeter()== null ? existingCustomer.getMeter() : customer.getMeter());
        existingCustomer.setSupplier(customer.getSupplier()== null ? existingCustomer.getSupplier() : customer.getSupplier());
        if(existingCustomer.getCurrReading()!= null){
            updateCurrentReading(existingCustomer.getCId(),existingCustomer.getCurrReading());
        }
        log.info("Customer data is updated");
        return customerRepository.save(existingCustomer);
    }

    /**
     * to Update Current reading in a bill and to get the Bill Amount
     * @param cId Customer's ID
     * @param currReading - current reading of the bill
     * @return Customer's Details
     */
    @Override
    public Customer updateCurrentReading(final Long cId , final Long currReading) throws Exception{
        Optional<Customer> customerOptional = customerRepository.findById(cId);
        if(customerOptional.isEmpty()){
            log.info("Invalid Customer Id");
            throw new IdNotFoundException("Customer with id- " + cId+ "Not found");
        }
        Customer customer = customerOptional.get();
        if(customer.getCurrReading()>currReading){
            log.info("Invalid Current Reading");
            throw new InvalidReadingException("Please enter a valid current reading as current reading"
                    +" cannot be less than last reading");
        }
        log.info("Updating last and current reading");
        customer.setLastReading(customer.getCurrReading());
        customer.setCurrReading(currReading);
        log.info("Last and current readings are updated");
        double billAmount = 0d;
        long netUnitConsumed = customer.getCurrReading()- customer.getLastReading();
        if(netUnitConsumed<100){
            billAmount = 100*3;
        }
        else if(netUnitConsumed<200){
            billAmount = 100*3 + (netUnitConsumed-100)*5;
        }
        else if(netUnitConsumed<300){
            billAmount = 100*3 + 200*5 + (netUnitConsumed-200)*6;
        }
        else if(netUnitConsumed<400){
            billAmount = 100*3 + 200*5 + 300*6 +(netUnitConsumed-300)*7;
        }
        else if(netUnitConsumed<500){
            billAmount = 100*3 + 200*5 + 300*6 + 400*7+ (netUnitConsumed-400)*7.5;
        }
        else if(netUnitConsumed>500){
            billAmount = 100*3 + 200*5 + 300*6 + 400*7+ 500*7.5 + (netUnitConsumed-500)*8;
        }
        double finalBillAmouint = billAmount>= customer.getMeter().getMinBillAmount()? billAmount : customer.getMeter().getMinBillAmount();
        customer.setBillAmount(finalBillAmouint);

        log.info("Customer Data is Updated");
        return customerRepository.save(customer);
    }
}
