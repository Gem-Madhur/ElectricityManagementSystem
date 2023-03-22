package com.demoproject.ems.service;

import com.demoproject.ems.entity.Customer;
import com.demoproject.ems.entity.Meter;
import com.demoproject.ems.entity.Supplier;
import com.demoproject.ems.repository.CustomerRepository;
import com.demoproject.ems.repository.MeterRepository;
import com.demoproject.ems.repository.SupplierRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
public class CustomerExcelService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MeterRepository meterRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * @param is
     * @return
     * @throws IOException
     */
    public void importData(InputStream is) throws IOException, ParseException {
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Customer customer = new Customer();
            customer.setCustomerName(row.getCell(0).getStringCellValue());
            customer.setCustomerAddress(row.getCell(1).getStringCellValue());
            Date date = formatter.parse(row.getCell(2).getStringCellValue());
            customer.setConnectionDate(date);
            customer.setLastReading((long) row.getCell(3).getNumericCellValue());
            customer.setCurrentReading((long) row.getCell(4).getNumericCellValue());
//            customer.setLastReading(Long.parseLong(row.getCell(3).getStringCellValue()));
//            customer.setCurrentReading(Long.parseLong(row.getCell(4).getStringCellValue()));
            long mid = (long) row.getCell(5).getNumericCellValue();
            long sid = (long) row.getCell(6).getNumericCellValue();
            Optional<Meter> meter = meterRepository.findById(mid);
            meterRepository.save(meter.get());
            Optional<Supplier> supplier = supplierRepository.findById(sid);
            supplierRepository.save(supplier.get());
            customer.setSupplier(supplier.get());
            customer.setMeter(meter.get());
            customerRepository.save(customer);
        }
    }
}
