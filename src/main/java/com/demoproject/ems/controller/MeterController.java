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
@RequestMapping(path = "/meters")
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
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Meter>> getMeters() throws DataNotFoundException{
        return new ResponseEntity<>(meterServiceIm.getMeters(), HttpStatus.OK);
    }

    /**
     * to get Meters details with Meter's ID
     *
     * @param meterId - Meter's ID
     * @return ResponseEntity
     */
    @GetMapping(path = "/meterById/{meterId}", produces = "application/json")
    public ResponseEntity<Meter> getMeterById(@PathVariable final Long meterId) throws IdNotFoundException {
        return new ResponseEntity<>(meterServiceIm.getMeterById(meterId), HttpStatus.OK);
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
     *
     * @param meterId - Meter's ID
     * @param meter   - Meter's Details
     * @return ResponseEntity
     */
    @PutMapping(path = "/update/{meterId}")
    public ResponseEntity<Meter> updateMeter(@PathVariable final Long meterId, @RequestBody final Meter meter) throws IdNotFoundException {
        return new ResponseEntity<>(meterServiceIm.updateMeter(meterId, meter), HttpStatus.OK);
    }

    /**
     * to Delete a Meter
     *
     * @param meterId - Meter's ID
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/delete/{meterId}")
    public ResponseEntity<String> deleteMeter(@PathVariable final Long meterId) throws IdNotFoundException {
        meterServiceIm.deleteMeter(meterId);
        return new ResponseEntity<>("Meter Deleted with ID- " + meterId, HttpStatus.OK);
    }
}
