package com.demoproject.ems.service;

import com.demoproject.ems.entity.Supplier;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;

import java.util.List;

public interface SupplierService {
    List<Supplier> getSuppliers() throws ResourceNotFoundException , DataNotFoundException;

    Supplier getSupplierById(Long sId) throws IdNotFoundException;

    Supplier addSupplier(Supplier supplier) throws ResourceNotFoundException;

    Supplier updateSupplierDetails(Long sId , Supplier supplier) throws IdNotFoundException;

    void deleteSupplier(Long sId) throws IdNotFoundException;
}
