package com.demoproject.ems.service;

import com.demoproject.ems.entity.Supplier;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.repository.SupplierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SupplierServiceIm implements SupplierService{

    /**
     * Autowired supplierRepository
     */
    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * to get List of all Suppliers
     * @return List of Suppliers
     */
    @Override
    public List<Supplier> getSuppliers() throws DataNotFoundException{
        List<Supplier> supplierList = supplierRepository.findAll();
        if(supplierList.size()==0){
            log.info("Supplier list is empty");
            throw new DataNotFoundException("Please enter Suppliers in list");
        }
        log.info(supplierList.size()+" total suppliers found");
        return supplierList;
    }

    /**
     * to get Details of Supplier with Supplier's ID
     * @param sId - Supplier's ID
     * @return Supplier's Details
     */
    @Override
    public Supplier getSupplierById(final Long sId) throws IdNotFoundException{
        Optional<Supplier> supplier = supplierRepository.findById(sId);
        if(supplier.isEmpty()){
            log.info("Invalid Supplier Id");
            throw new IdNotFoundException("Supplier "+ sId +" not found");
        }
        log.info("Supplier found with id- " +sId);
        return supplier.get();
    }

    /**
     * to Add a new Supplier
     * @param supplier - Supplier's Details
     * @return Supplier's Details
     */
    @Override
    public Supplier addSupplier(final Supplier supplier) throws ResourceNotFoundException{
        if(supplier.getSName() == null){
            log.info("Supplier name is missing");
            throw new ResourceNotFoundException("Please enter valid Supplier name.");
        }else if(supplier.getSArea()== null){
            log.info("Missing Supplier Area");
            throw new ResourceNotFoundException("Please enter Supplier Area.");
        }
        log.info("Supplier has been added");
        return supplierRepository.save(supplier);
    }

    /**
     * to Update Supplier Details
     * @param sId - Supplier's ID
     * @param supplier - Supplier's Details
     * @return Supplier's Details
     */
    @Override
    public Supplier updateSupplierDetails(final Long sId , final Supplier supplier) throws IdNotFoundException{
        Optional<Supplier> supplierOptional = supplierRepository.findById(sId);
        if(supplierOptional.isEmpty()){
            log.info("Invalid Supplier ID");
            throw new IdNotFoundException("Supplier Not found for id-"+ sId + ". Please enter a valid supplier ID");
        }
        log.info("Supplier Found, updating the details");
        Supplier existingSupplier = supplierOptional.get();
        existingSupplier.setSName(supplier.getSName() == null ? existingSupplier.getSName() : supplier.getSName());
        existingSupplier.setSArea(supplier.getSArea() == null ? existingSupplier.getSArea() : supplier.getSArea());
        log.info("Supplier data is updated");
        return supplierRepository.save(existingSupplier);
    }

    /**
     * to Delete a Supplier
     * @param sId -Supplier's ID
     */
    @Override
    public void deleteSupplier(final Long sId) throws IdNotFoundException{
        Optional<Supplier> supplierOptional = supplierRepository.findById(sId);
        if(supplierOptional.isEmpty()){
            log.info("Invalid Supplier ID");
            throw new IdNotFoundException("Supplier with id-" + sId + " not found");
        }
        log.info("customer with ID-" + sId  + " is deleted");
        supplierRepository.deleteById(sId);
    }
}
