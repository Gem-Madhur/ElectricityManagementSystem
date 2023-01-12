package com.demoproject.ems.controller;

import com.demoproject.ems.entity.Meter;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.service.MeterServiceIm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class MeterController {

    /**
     * Autowired meterServiceIm
     */
    @Autowired
    private MeterServiceIm meterServiceIm;

    /**
     * to get List of all Meters
     * @return ResponseEntity
     */
    @GetMapping(path = "/meters",produces = "application/json")
    public ResponseEntity<List<Meter>> getMeters() throws DataNotFoundException{
        return new ResponseEntity<>(meterServiceIm.getMeters(), HttpStatus.OK);
    }

    /**
     * to get Meters details with Meter's ID
     * @param mId - Meter's ID
     * @return ResponseEntity
     */
    @GetMapping(path = "/meter/{mId}",produces = "application/json")
    public ResponseEntity<Meter> getMeterById(@PathVariable final Long mId) throws IdNotFoundException{
        return new ResponseEntity<>(meterServiceIm.getMeterById(mId),HttpStatus.OK);
    }

    /**
     * to Add a new Meter
     * @param meter - Meter's Details
     * @return ResponseEntity
     */
    @PostMapping(path = "/addMeter",produces = "application/json",consumes ="application/json")
    public ResponseEntity<Meter> addMeter(@RequestBody final Meter meter) throws ResourceNotFoundException{
        return new ResponseEntity<>(meterServiceIm.addMeter(meter),HttpStatus.CREATED);
    }

    /**
     * to Update Meter Details
     * @param mId - Meter's ID
     * @param meter - Meter's Details
     * @return ResponseEntity
     */
    @PutMapping(path = "/meters/update/{mId}")
    public ResponseEntity<Meter> updateMeter(@PathVariable final Long mId,@RequestBody final Meter meter) throws IdNotFoundException{
        return new ResponseEntity<>(meterServiceIm.updateMeter(mId,meter),HttpStatus.OK);
    }

    /**
     * to Delete a Meter
     * @param mId - Meter's ID
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/meters/delete/{mId}")
    public ResponseEntity<HttpStatus> deleteMeter(@PathVariable final Long mId) throws IdNotFoundException{
        meterServiceIm.deleteMeter(mId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
