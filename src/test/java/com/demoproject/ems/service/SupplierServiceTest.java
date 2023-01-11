package com.demoproject.ems.service;


import com.demoproject.ems.entity.Supplier;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.repository.SupplierRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
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
@WebMvcTest(SupplierService.class)
public class SupplierServiceTest {


    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private SupplierServiceIm supplierService;

    @Test
    public void getSuppliersTest () throws DataNotFoundException {
        List<Supplier> supplierList = new ArrayList<>();
        supplierList.add(new Supplier(1100L, "Tata", "Rural"));
        supplierList.add(new Supplier(1101L, "Adani", "Urban"));
        when(supplierRepository.findAll()).thenReturn(supplierList);
        assertNotNull(supplierList);
        assertEquals(2, supplierService.getSuppliers().size());
        verify(supplierRepository).findAll();
    }

    @Test
    public void getSuppliersExceptionTest() {
        List<Supplier> supplierList = new ArrayList<>();
        when(supplierRepository.findAll()).thenReturn(supplierList);
        assertThatThrownBy(() -> supplierService.getSuppliers())
                .isInstanceOf(DataNotFoundException.class);
        verify(supplierRepository).findAll();

    }

    @Test
    public void getSupplierByIdTest() throws IdNotFoundException {
        Supplier supplier = new Supplier(1100L, "Tata", "Urban");
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        assertEquals(1100, supplierService.getSupplierById(1100L).getSId());
        assertEquals("Tata", supplierService.getSupplierById(1100L).getSName());
        verify(supplierRepository, times(2)).findById(1100L);
    }

    @Test
    public void getSupplierByIdExceptionTest() {
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> supplierService.getSupplierById(1L))
                .isInstanceOf(IdNotFoundException.class);
        verify(supplierRepository).findById(1L);
    }

    @Test
    public void addSupplierTest() throws ResourceNotFoundException {
        Supplier supplier = new Supplier(1100L, "Tata", "Urban");
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        Supplier newSupplier = supplierService.addSupplier(supplier);
        assertNotNull(newSupplier);
        assertEquals(supplier, newSupplier);
        verify(supplierRepository).save(supplier);
    }

    @Test
    public void updateSupplierByIdTest() throws Exception {
        Supplier supplier = new Supplier(1100L, "Tata", "Urban");
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        supplier.setSName("Tata");
        Supplier existingSupplier = supplierService.updateSupplierDetails(supplier.getSId(), supplier);
        assertNotNull(existingSupplier);
        assertEquals("Tata", supplier.getSName());
        verify(supplierRepository).findById(1100L);
        verify(supplierRepository).save(supplier);
    }
    @Test
    public void updateSupplierDetailsTest() {
        Supplier supplier = new Supplier(1100L, "Tata", "Urban");
        when(supplierRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> supplierService.updateSupplierDetails(1100L, supplier))
                .isInstanceOf(IdNotFoundException.class);
        verify(supplierRepository).findById(1100L);

    }
    @Test
    public void deleteSupplierTest() throws IdNotFoundException {
        Supplier supplier = new Supplier(1100L, "Tata", "Rural");

        when(supplierRepository.findById(anyLong())).thenReturn(Optional.of(supplier));
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);
        supplierService.deleteSupplier(1L);
        verify(supplierRepository).findById(1L);
        verify(supplierRepository).deleteById(1L);

    }
}

