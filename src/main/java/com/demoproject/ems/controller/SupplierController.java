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
@RequestMapping(path = "/suppliers")
public class SupplierController {

    /**
     * Autowired supplierServiceIm.
     */
    @Autowired
    private SupplierServiceIm supplierServiceIm;

    /**
     * to get List of all Suppliers.
     *
     * @return ResponseEntity
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Supplier>> getSuppliers() throws DataNotFoundException {
        return new ResponseEntity<>(supplierServiceIm.getSuppliers(), HttpStatus.OK);
    }

    /**
     * to get Details of Supplier with Supplier's ID.
     *
     * @param supplierId - Supplier's ID
     * @return ResponseEntity
     */
    @GetMapping(path = "/supplierById/{supplierId}", produces = "application/json")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable final Long supplierId) throws IdNotFoundException {
        return new ResponseEntity<>(supplierServiceIm.getSupplierById(supplierId), HttpStatus.OK);
    }

    /**
     * to Add a new Supplier.
     *
     * @param supplier - Supplier's Details
     * @return ResponseEntity
     */
    @PostMapping(path = "/addSupplier", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Supplier> addSupplier(@RequestBody final Supplier supplier) throws ResourceNotFoundException {
        return new ResponseEntity<>(supplierServiceIm.addSupplier(supplier), HttpStatus.CREATED);
    }

    /**
     * to Update Supplier Details.
     *
     * @param supplierId - Supplier's ID
     * @param supplier   - Supplier's Details
     * @return ResponseEntity
     */
    @PutMapping(path = "/update/{supplierId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Supplier> updateSupplierDetails(@PathVariable final Long supplierId, @RequestBody final Supplier supplier) throws IdNotFoundException {
        return new ResponseEntity<>(supplierServiceIm.updateSupplierDetails(supplierId, supplier), HttpStatus.OK);
    }

    /**
     * to Delete a Supplier.
     *
     * @param supplierId -Supplier's ID
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/delete/{supplierId}")
    public ResponseEntity<String> deleteSupplier(@PathVariable final Long supplierId) throws IdNotFoundException {
        supplierServiceIm.deleteSupplier(supplierId);
        return new ResponseEntity<>("Supplier Deleted with ID- " + supplierId, HttpStatus.OK);
    }
}
