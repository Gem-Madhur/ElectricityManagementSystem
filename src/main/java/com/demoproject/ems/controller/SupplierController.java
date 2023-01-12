package com.demoproject.ems.controller;

import com.demoproject.ems.entity.Supplier;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.service.SupplierServiceIm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SupplierController {

    /**
     * Autowired supplierServiceIm
     */
    @Autowired
    private SupplierServiceIm supplierServiceIm;

    /**
     * to get List of all Suppliers
     * @return ResponseEntity
     */
    @GetMapping(path = "/suppliers" , produces = "application/json")
    public ResponseEntity<List<Supplier>> getSuppliers() throws DataNotFoundException{
        return new ResponseEntity<>(supplierServiceIm.getSuppliers(), HttpStatus.OK);
    }

    /**
     * to get Details of Supplier with Supplier's ID
     * @param sId - Supplier's ID
     * @return ResponseEntity
     */
    @GetMapping(path = "/supplierById/{sId}" , produces = "application/json")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable final Long sId) throws IdNotFoundException{
        return new ResponseEntity<>(supplierServiceIm.getSupplierById(sId),HttpStatus.OK);
    }

    /**
     * to Add a new Supplier
     * @param supplier - Supplier's Details
     * @return ResponseEntity
     */
    @PostMapping(path = "/addSupplier",produces = "application/json",consumes = "application/json")
    public ResponseEntity<Supplier> addSupplier(@RequestBody final Supplier supplier) throws ResourceNotFoundException{
        return new ResponseEntity<>(supplierServiceIm.addSupplier(supplier),HttpStatus.CREATED);
    }

    /**
     * to Update Supplier Details
     * @param sId - Supplier's ID
     * @param supplier - Supplier's Details
     * @return ResponseEntity
     */
    @PutMapping(path = "/suppliers/update/{sId}",produces = "application/json",consumes = "application/json")
    public ResponseEntity<Supplier> updateSupplierDetails(@PathVariable final Long sId ,@RequestBody final Supplier supplier) throws IdNotFoundException{
        return new ResponseEntity<>(supplierServiceIm.updateSupplierDetails(sId,supplier),HttpStatus.OK);
    }

    /**
     * to Delete a Supplier
     * @param sId -Supplier's ID
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/suppliers/delete/{sId}")
    public  ResponseEntity<HttpStatus> deleteSupplier(@PathVariable final Long sId) throws IdNotFoundException{
        supplierServiceIm.deleteSupplier(sId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
