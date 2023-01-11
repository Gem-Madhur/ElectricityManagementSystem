package com.demoproject.ems.service;

import com.demoproject.ems.entity.Meter;
import com.demoproject.ems.exception.DataNotFoundException;
import com.demoproject.ems.exception.IdNotFoundException;
import com.demoproject.ems.exception.ResourceNotFoundException;
import com.demoproject.ems.repository.MeterRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = AutoConfigureMockMvc.class)
@WebMvcTest(MeterService.class)
public class MeterServiceTest {

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private MeterServiceIm meterService;

    @Test
    public void getMetersTest () throws DataNotFoundException {
        List<Meter> meterList = new ArrayList<>();
        meterList.add(new Meter(1L, 1F, 3000F));
        meterList.add(new Meter(2L, 2F, 4000F));
        when(meterRepository.findAll()).thenReturn(meterList);
        assertNotNull(meterList);
        assertEquals(2, meterService.getMeters().size());
        verify(meterRepository).findAll();
    }

    @Test
    public void getMetersExceptionTest() {
        List<Meter> meterList = new ArrayList<>();
        when(meterRepository.findAll()).thenReturn(meterList);
        assertThatThrownBy(() -> meterService.getMeters())
                .isInstanceOf(DataNotFoundException.class);
        verify(meterRepository).findAll();
    }
    @Test
    public void getMeterByIdTest() throws IdNotFoundException {
        Meter meter = new Meter(1L, 1f, 3000F);
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(meter));
        assertEquals(3000, meterService.getMeterById(1L).getMinBillAmount());
        verify(meterRepository).findById(1L);
    }

    @Test
    public void getMeterByIdExceptionTest() {
        when(meterRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> meterService.getMeterById(1L))
                .isInstanceOf(IdNotFoundException.class);
        verify(meterRepository).findById(1L);
    }

    @Test
    public void addMeterTest() throws ResourceNotFoundException {
        Meter meter = new Meter(1L, 1F, 3000F);
        when(meterRepository.save(any(Meter.class))).thenReturn(meter);
        Meter newMeter = meterService.addMeter(meter);
        assertNotNull(newMeter);
        assertEquals(meter, newMeter);
        verify(meterRepository).save(meter);
    }

    @Test
    public void updateMeterTest() throws Exception {
        Meter meter = new Meter(1L, 1F, 3000F);
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(meter));
        when(meterRepository.save(any(Meter.class))).thenReturn(meter);
        meter.setMinBillAmount(3500F);
        Meter existingMeter = meterService.updateMeter(meter.getMId(), meter);
        assertNotNull(existingMeter);
        assertEquals(3500, meter.getMinBillAmount());
        verify(meterRepository).findById(1L);
        verify(meterRepository).save(meter);
    }

    @Test
    public void updateMeterExceptionTest() {
        Meter meter = new Meter(1L, 1F, 3000F);
        when(meterRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> meterService.updateMeter(1L, meter))
                .isInstanceOf(IdNotFoundException.class);
        verify(meterRepository).findById(1L);
    }

    @Test
    public void deleteMeterTest() throws IdNotFoundException {
        Meter meter = new Meter(1L, 1F, 3000F);
        when(meterRepository.findById(anyLong())).thenReturn(Optional.of(meter));
        when(meterRepository.save(any(Meter.class))).thenReturn(meter);
        meterService.deleteMeter(1L);
        verify(meterRepository).findById(1L);
        verify(meterRepository).deleteById(1L);
    }
}

