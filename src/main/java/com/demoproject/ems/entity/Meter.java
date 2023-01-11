package com.demoproject.ems.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meter_tbl")

public class Meter {

    /**
     * meter_id is the primary key for Meter_tbl
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meter_id")
    private Long mId;

    @Column(name = "meter_load")
    private Float mLoad;

    @Column(name = "min_bill_amount")
    private Float minBillAmount;
}
