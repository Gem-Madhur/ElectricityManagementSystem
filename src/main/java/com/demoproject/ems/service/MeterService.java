package com.demoproject.ems.service;

import com.demoproject.ems.entity.Meter;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;

import java.util.List;

public interface MeterService {
    List<Meter> getMeters () throws DataNotFoundException;

    Meter getMeterById(Long meterId) throws IdNotFoundException;

    Meter addMeter(Meter meter) throws ResourceNotFoundException;

    Meter updateMeter(Long meterId, Meter meter) throws IdNotFoundException, ResourceNotFoundException;

    void deleteMeter(Long meterId) throws IdNotFoundException;
}
