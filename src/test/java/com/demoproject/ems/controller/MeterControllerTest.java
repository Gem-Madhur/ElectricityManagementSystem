package com.demoproject.ems.controller;

import com.demoproject.ems.entity.Meter;
import com.demoproject.ems.service.MeterServiceIm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = AutoConfigureMockMvc.class)
@WebMvcTest(MeterController.class)
public class MeterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private MeterServiceIm meterService;

    @InjectMocks
    private MeterController meterController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init(){
        mockMvc = MockMvcBuilders.standaloneSetup(meterController).build();
    }

    @Test
    void getMetersTest() throws Exception{
        List<Meter> meterList= new ArrayList<>();
        meterList.add(new Meter(1010L, 2F, 4000F));
        meterList.add(new Meter(1011L, 1F, 3000F));
        when(meterService.getMeters()).thenReturn(meterList);
        this.mockMvc.perform(get("/meters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(meterList.size())));
        verify(meterService).getMeters();
    }

    @Test
    void getMeterByIdTest() throws Exception{
        Meter meter1 = new Meter(1010L, 1F, 3000F);
        when(meterService.getMeterById(anyLong())).thenReturn(meter1);
        this.mockMvc.perform(get("/meterById/{meterId}", 1010L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mLoad", is(meter1.getMLoad())))
                .andExpect(jsonPath("$.minBillAmount", is(meter1.getMinBillAmount())));
        verify(meterService).getMeterById(1010L);
    }

    @Test
    void addMeterTest() throws Exception {
        Meter meter = new Meter(1L, 1F, 3000F);
        when(meterService.addMeter(any(Meter.class))).thenReturn(meter);
        this.mockMvc.perform(post("/addMeter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meter)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.mLoad", is(meter.getMLoad())))
                .andExpect(jsonPath("$.minBillAmount", is(meter.getMinBillAmount())));
        verify(meterService).addMeter(meter);
    }

    @Test
    void updateMeterTest() throws Exception {
        Meter meter = new Meter(1010L, 1F, 3000F);
        when(meterService.updateMeter(anyLong(), any(Meter.class))).thenReturn(meter);
        this.mockMvc.perform(put("/meters/update/{mId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(meter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mLoad", is(meter.getMLoad())))
                .andExpect(jsonPath("$.minBillAmount", is(meter.getMinBillAmount())));
        verify(meterService).updateMeter(1L, meter);
    }
    @Test
    void deleteMeterTest() throws Exception {
        doNothing().when(meterService).deleteMeter(anyLong());
        this.mockMvc.perform(delete("/meters/delete/{mId}", 1010L))
                .andExpect(status().isOk());
        verify(meterService).deleteMeter(1010L);
    }


}
