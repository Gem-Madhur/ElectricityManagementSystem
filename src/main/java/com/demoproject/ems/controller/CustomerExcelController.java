package com.demoproject.ems.controller;

import com.demoproject.ems.service.CustomerExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class CustomerExcelController {

    @Autowired
    private CustomerExcelService excelService;

    @PostMapping(value = "/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addExcel(@RequestPart() final MultipartFile file) throws Exception {
        excelService.importData(file.getInputStream());
        return ResponseEntity.status(HttpStatus.OK).body("Done");
    }
}
