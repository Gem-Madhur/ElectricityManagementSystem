package com.demoproject.ems.service;

import com.demoproject.ems.entity.Customer;
import com.demoproject.ems.entity.Meter;
import com.demoproject.ems.entity.Supplier;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.InvalidReadingException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = AutoConfigureMockMvc.class)
@WebMvcTest(CustomerService.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceIm customerService;

    @Test
    public void getCustomersTest () throws DataNotFoundException {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1L, "Madhur", "BW", new Date(), 3L,
                7L, 0D, null, null));
        customerList.add(new Customer(2L, "Aman", "BN", new Date(), 0L,
                0L, 0D, null, null));
        when(customerRepository.findAll()).thenReturn(customerList);
        assertNotNull(customerList);
        List<Customer> customers = customerService.getCustomers();
        assertEquals(2, customers.size());
        verify(customerRepository).findAll();
    }

    @Test
    public void getCustomersExceptionTest() {
        List<Customer> customerList = new ArrayList<>();
        when(customerRepository.findAll()).thenReturn(customerList);
        assertThatThrownBy(() -> customerService.getCustomers())
                .isInstanceOf(DataNotFoundException.class);
        verify(customerRepository).findAll();
    }

    @Test
    public void getCustomerById() throws IdNotFoundException {
        Customer customer = new Customer(1L, "Madhur", "BW", new Date(), 0L,
                0L, 0D, null, null);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        assertEquals(1, customerService.getCustomerById(1L).getCId());
        verify(customerRepository).findById(1L);
    }

    @Test
    public void getCustomerByIdExceptionTest() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> customerService.getCustomerById(1L))
                .isInstanceOf(IdNotFoundException.class);
        verify(customerRepository).findById(1L);
    }

    @Test
    public void addCustomerTest() throws ResourceNotFoundException {
        Customer customer = new Customer(1L, "Madhur", "BW", null, 0L,
                0L, 0d, new Meter(1L,1F, 3000F ),
                new Supplier(1L, "Tata", "Urban"));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer newCustomer = customerService.addCustomer(customer);
        assertNotNull(newCustomer);
        assertEquals(customer, newCustomer);
        verify(customerRepository).save(customer);
    }

    @Test
    public void updateCustomerTest() throws Exception {
        Customer customer1 = new Customer(1L, "Madhur", "BW", null, 0L,
                0L, 0d, new Meter(1L,2F, 4000F ),
                new Supplier(2L, "Adani", "Rural"));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer1));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer1);
        customer1.setCName("Aman");
        Customer existingCustomer = customerService.updateCustomerById(customer1.getCId(), customer1);
        assertNotNull(existingCustomer);
        assertEquals("Aman", customer1.getCName());
        verify(customerRepository, times(2)).findById(1L);
    }

    @Test
    public void updateCustomerExceptionTest() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        Customer customer = new Customer(1L, "Madhur", "BW", null, 0L,
                0L, 0d, new Meter(1L, 1F, 3000F),
                new Supplier(1L, "Tata", "urban"));
        assertThatThrownBy(() -> customerService.updateCustomerById(1L, customer))
                .isInstanceOf(IdNotFoundException.class);
        verify(customerRepository).findById(1L);
    }

    @Test
    public void updateCurrentReadingExceptionTest1() {
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> customerService.updateCurrentReading(1L, 300L))
                .isInstanceOf(IdNotFoundException.class);
        verify(customerRepository).findById(1L);
    }

    @Test
    public void updateCurrentReadingExceptionTest2() {
        Customer customer = new Customer(1L, "Madhur", "BW", null, 0L,
                300L, 0d, new Meter(1L, 1F, 3000F),
                new Supplier(1L, "Tata", "Urban"));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        assertThatThrownBy(() -> customerService.updateCurrentReading(1L, 250L))
                .isInstanceOf(InvalidReadingException.class);
        verify(customerRepository).findById(1L);
    }
    @Test
    public void updateCurrentReadingTest() throws Exception {
        Customer customer = new Customer(1L, "Madhur", "BW", null, 0L,
                300L, 0d, new Meter(1L, 2F, 3000F),
                new Supplier(1L, "Tata", "Urban"));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        customerService.updateCurrentReading(1L, 500L);
        assertEquals(3000, customerRepository.findById(1L).get().getBillAmount());
        verify(customerRepository, times(2)).findById(1L);
        verify(customerRepository).save(customer);
    }

    @Test
    public void deleteCustomer() throws IdNotFoundException {
        Customer customer = new Customer(1L, "Madhur", "BW", null, 0L,
                0L, 0d, new Meter(1L, 1F, 3000F),
                new Supplier(1L, "Tata", "Urban"));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        customerService.deleteCustomer(1L);
        verify(customerRepository).findById(1L);
        verify(customerRepository).deleteById(1L);
    }
}

