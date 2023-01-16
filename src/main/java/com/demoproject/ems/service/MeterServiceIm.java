package com.demoproject.ems.service;

import com.demoproject.ems.entity.Meter;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.repository.MeterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MeterServiceIm implements MeterService{

    /**
     * Autowired meterRepository
     */
    @Autowired
    private MeterRepository meterRepository;

    /**
     * to get List of all Meters
     * @return List of Meters
     */
    @Override
    public List<Meter> getMeters() throws DataNotFoundException{
        List<Meter> meterList = meterRepository.findAll();
        if(meterList.size()==0){
            log.info("No Customer Found");
            throw new DataNotFoundException("List is Empty");
        }
        log.info("Successfully received the list of meters");
        return meterList;
    }

    /**
     * to get Meters details with Meter's ID
     *
     * @param meterId - Meter's ID
     * @return Meter's Details
     */
    @Override
    public Meter getMeterById(final Long meterId) throws IdNotFoundException {
        Optional<Meter> meter = meterRepository.findById(meterId);
        if (meter.isEmpty()) {
            log.info("Invalid Meter Id");
            throw new IdNotFoundException("No meter found for meter id : " + meterId + ". \n Please check the meter ID.");
        }
        log.info("Meter found.");
        return meter.get();
    }

    /**
     * to Add a new Meter
     * @param meter - Meter's Details
     * @return Meter's Details
     */
    @Override
    public Meter addMeter(final Meter meter) throws ResourceNotFoundException {
        if (meter.getMeterLoad() == null) {
            log.info("Meter Load Value is missing");
            throw new ResourceNotFoundException("Please enter the value for Meter Load.");
        } else if (meter.getMinBillAmount() == null) {
            log.info("Minimum Bill Amount is missing");
            throw new ResourceNotFoundException("Please enter the Minimum Bill Amount.");
        }
        log.info("Meter Added");
    meterRepository.save(meter);
    return meter;
    }

    /**
     * to Update Meter Details
     *
     * @param meterId - Meter's ID
     * @param meter   - Meter's Details
     * @return Meter's Details
     */
    @Override
    public Meter updateMeter(final Long meterId, final Meter meter) throws IdNotFoundException {
        Optional<Meter> meterOptional = meterRepository.findById(meterId);
        if (meterOptional.isEmpty()) {
            log.info("Invalid Meter ID");
            throw new IdNotFoundException("Customer with " + meterId + " ID not found");
        }
        Meter existingMeter = meterOptional.get();
        existingMeter.setMeterLoad(meter.getMeterLoad() == null ? existingMeter.getMeterLoad() : meter.getMeterLoad());
        existingMeter.setMinBillAmount(meter.getMinBillAmount() == null ? existingMeter.getMinBillAmount() : meter.getMinBillAmount());
        log.info("Meter has been updated.");
        return meterRepository.save(existingMeter);
    }

    /**
     * to Delete a Meter
     *
     * @param meterId - Meter's ID
     */
    @Override
    public void deleteMeter(final Long meterId) throws IdNotFoundException {
        Optional<Meter> meter = meterRepository.findById(meterId);
        if (meter.isEmpty()) {
            log.info("Invalid Meter ID");
            throw new IdNotFoundException("Meter not found with id- " + meterId);
        }
        log.info("Meter has been Deleted");
        meterRepository.deleteById(meterId);
    }
}
