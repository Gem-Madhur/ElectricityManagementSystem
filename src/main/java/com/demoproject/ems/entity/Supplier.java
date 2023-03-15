package com.demoproject.ems.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier")

public class Supplier {

    /**
     * supplier_id is the primary key for supplier_tbl
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;


    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "supplier_area")
    private String supplierArea;
}
