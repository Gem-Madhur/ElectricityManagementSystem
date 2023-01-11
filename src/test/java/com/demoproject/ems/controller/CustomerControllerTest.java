package com.demoproject.ems.controller;


import com.demoproject.ems.entity.Customer;
import com.demoproject.ems.service.CustomerServiceIm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = AutoConfigureMockMvc.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerServiceIm customerService;

    @InjectMocks
    private CustomerController customerController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getCustomersTest() throws Exception {
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(100L, "Madhur", "BW",new Date(), 1L, 5L,
                0D, null, null));
        customerList.add(new Customer(200L, "Aman", "BN", new Date(), 2L, 8L,
                0D, null, null));
        when(customerService.getCustomers()).thenReturn(customerList);
        this.mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(customerList.size())));
        verify(customerService, times(1)).getCustomers();
    }

    @Test
    void getCustomerByIdTest() throws Exception {
        Customer customer1 = new Customer(101L, "Madhur", "BW",
                null, 0L, 0L, 0d,
                null, null);
        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);
        this.mockMvc.perform(get("/customerById/{cId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cName", is(customer1.getCName())))
                .andExpect(jsonPath("$.cAddress", is(customer1.getCAddress())))
                .andExpect(jsonPath("$.connectionDate", is(customer1.getConnectionDate())))
                .andExpect(jsonPath("$.lastReading", is(customer1.getLastReading().intValue())))
                .andExpect(jsonPath("$.currtReading", is(customer1.getCurrReading().intValue())))
                .andExpect(jsonPath("$.billAmount", is(customer1.getBillAmount())))
                .andExpect(jsonPath("$.meter", is(customer1.getMeter())))
                .andExpect(jsonPath("$.supplier", is(customer1.getSupplier())));
        verify(customerService).getCustomerById(101L);
    }

    @Test
    void addCustomerTest() throws Exception {
        Customer customer1 = new Customer(5L, "Madhur", "BW", null, 0L,
                0L, 0d, null,null);
        when(customerService.addCustomer(any(Customer.class))).thenReturn(customer1);
        this.mockMvc.perform(post("/addCustomer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cName", is(customer1.getCName())))
                .andExpect(jsonPath("$.cAddress", is(customer1.getCAddress())));
        verify(customerService).addCustomer(customer1);
    }



    @Test
    void updateCustomerByIdTest() throws Exception {
        Customer customer1 = new Customer(8L, "Madhur", "BW", null, 0L,
                0L, 0d, null,null);
        when(customerService.updateCustomerById(anyLong(),any(Customer.class))).thenReturn(customer1);
        this.mockMvc.perform(put("/update/{cId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cName", is(customer1.getCName())))
                .andExpect(jsonPath("$.cAddress", is(customer1.getCAddress())));
        verify(customerService).updateCustomerById(8L, customer1);
    }

    @Test
    void deleteCustomerTest() throws Exception {
        doNothing().when(customerService).deleteCustomer(anyLong());
        this.mockMvc.perform(delete("/delete/{cId}", 8L))
                .andExpect(status().isOk());
        verify(customerService).deleteCustomer(8L);
    }

    @Test
    void updateCurrentReadingTest() throws Exception {
        Customer customer1 = new Customer(5L, "AMAN", "BN", null, 0L,
                0L, 0d, null, null);
        when(customerService.updateCurrentReading(anyLong(), anyLong())).thenReturn(customer1);
        this.mockMvc.perform(put("/customers")
                        .param("cId", "1")
                        .param("currReading", "500L"))
                .andExpect(status().isOk());
        verify(customerService).updateCurrentReading(1L, 500L);
    }
}