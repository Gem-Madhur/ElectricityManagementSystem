package com.demoproject.ems.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier_tbl")

public class Supplier {

    /**
     * supplier_id is the primary key for supplier_tbl
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long sId;


    @Column(name = "supplier_name")
    private String sName;

    @Column(name = "supplier_area")
    private String sArea;
}