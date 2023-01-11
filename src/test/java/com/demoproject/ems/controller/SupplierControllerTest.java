package com.demoproject.ems.controller;

import com.demoproject.ems.entity.Supplier;
import com.demoproject.ems.service.SupplierServiceIm;
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
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = AutoConfigureMockMvc.class)
@WebMvcTest(SupplierController.class)
public class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SupplierServiceIm supplierService;

    @InjectMocks
    private SupplierController supplierController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();

    }

    @Test
    void getSuppliersTest() throws Exception {
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(new Supplier(1100L, "Tata", "Urban"));
        supplierList.add(new Supplier(1101L, "Adani", "Rural"));

        when(supplierService.getSuppliers()).thenReturn(supplierList);
        this.mockMvc.perform(get("/suppliers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(supplierList.size())));
        verify(supplierService).getSuppliers();
    }

    @Test
    void getSupplierByIdTest() throws Exception {
        Supplier supplier1 = new Supplier(1100L, "Tata", "Urban");
        when(supplierService.getSupplierById(anyLong())).thenReturn(supplier1);
        this.mockMvc.perform(get("/supplier/{sId}", 1100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sName", is(supplier1.getSName())))
                .andExpect(jsonPath("$.sArea", is(supplier1.getSArea())));
        verify(supplierService).getSupplierById(1100L);
    }

    @Test
    void addSupplierTest() throws Exception {
        Supplier supplier1 = new Supplier(1100L, "Adani", "Rural");
        when(supplierService.addSupplier(any(Supplier.class))).thenReturn(supplier1);
        this.mockMvc.perform(post("/addSupplier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplier1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sName", is(supplier1.getSName())))
                .andExpect(jsonPath("$.sArea", is(supplier1.getSArea())));
        verify(supplierService).addSupplier(supplier1);
    }

    @Test
    void updateSupplierDetailTest() throws Exception {
        Supplier supplier1 = new Supplier(1100L, "Tata", "Urban");
        when(supplierService.updateSupplierDetails(anyLong(), any(Supplier.class))).thenReturn(supplier1);
        this.mockMvc.perform(put("/update/{sId}", 1100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplier1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sName", is(supplier1.getSName())))
                .andExpect(jsonPath("$.sArea", is(supplier1.getSArea())));
        verify(supplierService).updateSupplierDetails(1100L, supplier1);
    }

    @Test
    void deleteSupplierTest() throws Exception {
        doNothing().when(supplierService).deleteSupplier(anyLong());
        this.mockMvc.perform(delete("/delete/{sId}", 1100L))
                .andExpect(status().isOk());
        verify(supplierService).deleteSupplier(1100L);
    }
}

